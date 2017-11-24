package pt.ulusofona.es.g6.controller;


import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.SocketUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pt.ulusofona.es.g6.data.AgregadoFamiliar;
import pt.ulusofona.es.g6.data.Categoria;
import pt.ulusofona.es.g6.data.Despesa;

import pt.ulusofona.es.g6.data.StartUpUtils;
import pt.ulusofona.es.g6.form.DespesaForm;
import pt.ulusofona.es.g6.form.UploadForm;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.security.Principal;
import java.security.Security;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static pt.ulusofona.es.g6.form.UploadForm.UPLOAD_FOLDER;

@Controller
@Transactional
@RequestMapping("/despesa/")
public class DespesaController {

    static final String MSG_DESPESA_ADICIONADA = "<strong>Sucesso!</strong> Despesa adicionada";

    static final String MSG_DESPESA_ALTERADA = "<strong>Sucesso!</strong> Despesa alterada";

    static final String MSG_DESPESA_ELIMINADA = "<strong>Sucesso!</strong> Despesa eliminada";

    static final String MSG_DESPESA_MES_ANTERIOR = "<strong>Erro!</strong> Só pode eliminar as despesas do mês corrente";

    @PersistenceContext
    private EntityManager em;


    @RequestMapping(value = "inserir", method = RequestMethod.GET)
    public String abrirInserirDespesa(ModelMap model) {
        model.put("categorias", listarCategorias());
        model.put("despesasForm", new DespesaForm());
        return "inserirDespesa";
    }


    @RequestMapping(value = "/consultar/alterar/{id}", method = RequestMethod.GET)
    public String abrirInserirDespesa(ModelMap model, @PathVariable("id") long id) {
        Despesa despesa = em.find(Despesa.class, id);
        model.put("categorias", listarCategorias());
        model.put("despesasForm", new DespesaForm());
        model.put("despesa", despesa);
        return "inserirDespesa";
    }

    @RequestMapping(value = "inserir", method = RequestMethod.POST)
    public String inserirDespesa(@Valid @ModelAttribute("despesasForm") DespesaForm despesaForm,
                                 BindingResult bindingResult,
                                 ModelMap model,
                                 Principal user) {

        model.put("categorias", listarCategorias());

        if(bindingResult.hasErrors()) {
            return "inserirDespesa";
        }

        if(despesaForm.getId() != null) {
            inserirDespesa(despesaForm, em.find(Despesa.class, despesaForm.getId()), user.getName());
            model.addAttribute("message", MSG_DESPESA_ALTERADA);
        } else {
            inserirDespesa(despesaForm, user.getName());
            model.addAttribute("message", MSG_DESPESA_ADICIONADA);
        }
        return "inserirDespesa";
    }


    @RequestMapping(value = "consultar", method = RequestMethod.GET)
    public String listarDespesas(ModelMap model, Principal user) {
        boolean btnAgregado = false;

        for(String[] utilizadores : StartUpUtils.getAgregadoFamiliar()) {
            for(String utilizador : utilizadores) {
                if(utilizador.equals(user.getName()))
                    btnAgregado = true;
            }
        }

        model.put("agregado",btnAgregado);
        model.put("despesas", em.createQuery("SELECT c FROM Despesa c WHERE utilizador = '" + user.getName()+ "'  ORDER BY data", Despesa.class).getResultList());
        return "consultarDespesa";
    }

    @RequestMapping(value = "consultar/agregado", method = RequestMethod.POST)
    public String listarDespesasAgregado(ModelMap model, Principal user) {
        String agregado = "(";

        for(String utilizadores[] : StartUpUtils.getAgregadoFamiliar()){
            for(String utilizador : utilizadores){
                if(utilizador.equals(user.getName())){
                    for(String userGrava : utilizadores){
                        agregado += "'" + userGrava + "',";
                    }
                }
            }
        }

        agregado = agregado.substring(0,agregado.length()-1);
        agregado += ")";

        model.put("despesas", em.createQuery("SELECT c FROM Despesa c WHERE utilizador IN " + agregado + "  ORDER BY data", Despesa.class).getResultList());
        return "consultarDespesa";
    }


    @RequestMapping(value = "consultar/detalhes/{id}", method = RequestMethod.GET)
    public String listarDetalhesDaDespesa(ModelMap model, @PathVariable("id") long id) {
        model.put("despesa", em.find(Despesa.class, id));
        return "detalhesDespesa";
    }


    @RequestMapping(value = "consultar/eliminar/{id}", method = RequestMethod.POST)
    public String eliminarDespesa(ModelMap model, @PathVariable("id") long id) {

        Despesa despesa = em.find(Despesa.class, id);

        if(!validaDataParaEliminar(despesa.getData())) {
            model.addAttribute("messageError", MSG_DESPESA_MES_ANTERIOR);
            model.put("despesa", em.find(Despesa.class, id));
            return "detalhesDespesa";
        }

        em.remove(despesa);
        model.put("message", MSG_DESPESA_ELIMINADA);
        model.put("despesas", em.createQuery("SELECT c FROM Despesa c ORDER BY data", Despesa.class).getResultList());
        return "consultarDespesa";
    }


    @RequestMapping(value = "upload", method = RequestMethod.GET)
    public String getFileUploadForm(ModelMap model) {
        model.put("uploadForm" ,new UploadForm());
        return "uploadForm";
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String handleFileUpload(@Valid @ModelAttribute("userForm") UploadForm userForm,
                                   BindingResult bindingResult,
                                   @RequestParam("file") MultipartFile file,
                                   ModelMap model,
                                   Principal user){

        if (bindingResult.hasErrors()){
            return "uploadForm";
        }

        if (!file.isEmpty()) {
            try{

                byte[] bytes = file.getBytes();
                String completeData = new String(bytes);
                String[] rows = completeData.split(System.getProperty("line.separator"));

                inserirDespesaUpload(rows,user.getName());
                model.put("message", "Despesas inseridas com sucesso");
                return "uploadForm";

            } catch (Exception e){
                e.printStackTrace();
                model.put("message", "Falha no upload => " + e.getMessage());
                return "uploadForm";
            }
        } else {
            model.put("message", "Ficheiro vazio");
            return "uploadForm";
        }


    }

    private void inserirDespesaUpload(String[] rows,String username){

        for(String despesa : rows) {
            String[] columns = despesa.split(";");
            Despesa despesaObj = new Despesa("Indefinida",Double.parseDouble(columns[1]),columns[0], (columns.length-1==2) ? columns[2] : "",username);
            em.persist(despesaObj);
        }


    }


    private void inserirDespesa(DespesaForm despesaForm, String username) {
        inserirDespesa(despesaForm, new Despesa(), username);
    }


    private void inserirDespesa(DespesaForm despesaForm, Despesa despesa, String username) {
        despesa.setData(Calendar.getInstance().getTime());
        despesa.setCategoria(despesaForm.getCategoria());
        despesa.setValor(despesaForm.getValor());
        despesa.setDescricao(despesaForm.getDescricao());
        despesa.setLocalizacao(despesaForm.getLocalizacao());
        despesa.setUtilizador(username);
        em.persist(despesa);

        despesaForm.setCategoria(null);
        despesaForm.setValor(null);
        despesaForm.setDescricao(null);
        despesaForm.setLocalizacao(null);
    }


    private List<Categoria> listarCategorias() {
        return em.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();
    }

    private boolean validaDataParaEliminar(Date data) {
        boolean resultado = false;
        if(Calendar.getInstance().getTime().getMonth() == data.getMonth()) {
            resultado = true;
        }
        return resultado;
    }

}