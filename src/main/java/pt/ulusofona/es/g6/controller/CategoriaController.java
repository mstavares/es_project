package pt.ulusofona.es.g6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pt.ulusofona.es.g6.data.Categoria;
import pt.ulusofona.es.g6.data.StartUpUtils;
import pt.ulusofona.es.g6.form.CategoriaForm;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/categoria/")
public class CategoriaController {

    static final String MSG_CATEGORIA_ADICIONADA = "<strong>Sucesso!</strong> Categoria adicionada";

    static final String MSG_CATEGORIA_ALTERADA = "<strong>Sucesso!</strong> Categoria alterada";

    static final String MSG_CATEGORIA_ELIMINADA = "<strong>Sucesso!</strong> Categoria eliminada";

    static final String MSG_ALTERAR_CATEGORIA_PREDEF = "<strong>Erro!</strong> Não pode alterar as categorias predefinidas";

    static final String MSG_ELIMINAR_CATEGORIA_PREDEF = "<strong>Erro!</strong> Não pode eliminar as categorias predefinidas";

    @PersistenceContext
    private EntityManager em;


    @RequestMapping("inserir")
    public String abrirCategoria(ModelMap model) {
        model.put("categoriaForm", new CategoriaForm());
        return "inserirCategoria";
    }

    @RequestMapping(value = "inserir", method = RequestMethod.POST)
    public String submitFormInserirCategoria(@Valid @ModelAttribute("categoriaForm")
                                 CategoriaForm categoriaForm,
                                             BindingResult bindingResult,
                                             ModelMap model) {

        if(bindingResult.hasErrors()) {
            return "inserirCategoria";
        }

        if(categoriaForm.getId() != null) {
            Categoria categoria = em.find(Categoria.class, categoriaForm.getId());
            categoria.setCategoria(categoriaForm.getCategoria());
            em.persist(categoria);
            model.addAttribute("message", MSG_CATEGORIA_ALTERADA);
        } else {
            em.persist(new Categoria(categoriaForm.getCategoria()));
            model.addAttribute("message", MSG_CATEGORIA_ADICIONADA);
        }

        categoriaForm.setCategoria(null);
        return "inserirCategoria";
    }

    @RequestMapping(value = "consultar", method = RequestMethod.GET)
    public String listarCategorias(ModelMap model) {
        model.put("categorias", carregarCategorias());
        return "consultarCategorias";
    }

    @RequestMapping(value = "consultar/alterar/{id}", method = RequestMethod.GET)
    public String alterarCategoria(ModelMap model, @PathVariable("id") long id) {
        Categoria categoria = em.find(Categoria.class, id);

        if(StartUpUtils.eUmaCategoriaPredefinida(categoria.getCategoria())) {
            model.put("categorias", carregarCategorias());
            model.put("messageError", MSG_ALTERAR_CATEGORIA_PREDEF);
            return "consultarCategorias";
        }

        model.put("categoriaForm", new CategoriaForm());
        model.put("categoria", categoria);
        return "inserirCategoria";
    }

    @RequestMapping(value = "consultar/eliminar/{id}", method = RequestMethod.GET)
    public String eliminarCategoria(ModelMap model, @PathVariable("id") long id) {
        Categoria categoria = em.find(Categoria.class, id);

        if(StartUpUtils.eUmaCategoriaPredefinida(categoria.getCategoria())) {
            model.put("categorias", carregarCategorias());
            model.put("messageError", MSG_ELIMINAR_CATEGORIA_PREDEF);
            return "consultarCategorias";
        } else {
            em.remove(categoria);
            model.put("categorias", carregarCategorias());
            model.put("message", MSG_CATEGORIA_ELIMINADA);
        }

        return "consultarCategorias";
    }

    private List<Categoria> carregarCategorias() {
        return em.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();
    }

}
