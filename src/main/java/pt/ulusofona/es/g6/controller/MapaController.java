package pt.ulusofona.es.g6.controller;


import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pt.ulusofona.es.g6.data.Categoria;
import pt.ulusofona.es.g6.data.Despesa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Controller
@Transactional


public class MapaController {

    private static final int NUMERO_DE_LINHAS_DO_MAPA = 13;

    @PersistenceContext
    private EntityManager em;

    @RequestMapping(value ="home", method = RequestMethod.GET)
    public String abrirHome(ModelMap model) {

        List<Categoria> categorias = em.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();
        List<Despesa> despesas = em.createQuery("select c FROM Despesa c", Despesa.class).getResultList();

        double[][] resultados = new double[NUMERO_DE_LINHAS_DO_MAPA][categorias.size() + 1];
        double[] resultadosTotal = new double[categorias.size() + 1];
        String[] variacaoMensal = new String[NUMERO_DE_LINHAS_DO_MAPA];

        //----- Organiza lista -----
        for(Despesa despesa : despesas){
            for(int i = 0; i< categorias.size(); i++){
                if(categorias.get(i).getCategoria().equals(despesa.getCategoria())) {
                    resultados[despesa.getData().getMonth() + 1][i] += despesa.getValor();
                    //----- Calcula totais direita -----
                    resultados[despesa.getData().getMonth() + 1][categorias.size()] += despesa.getValor();
                    //----- Calcula totais inferiores -----
                    resultadosTotal[i] += despesa.getValor();
                    //----- Calcula soma dos totais -----
                    resultadosTotal[categorias.size()] += despesa.getValor();
                    break;
                }
            }
        }

        //----- Calcula variacoes -----
        for(int i = 1; i < NUMERO_DE_LINHAS_DO_MAPA; i++) {
            double mesAnterior = resultados[i-1][categorias.size()];
            double mesActual = resultados[i][categorias.size()];

            if(mesAnterior == 0){
                variacaoMensal[i] = "-";
            } else {
                Double variacao = ((mesActual-mesAnterior) / mesAnterior) * 100;
                variacaoMensal[i] = String.valueOf(variacao.intValue()) + "%";
            }

        }

        //----- Coloca variaveis no modelo ----
        model.put("categorias", categorias);
        model.put("resultados", resultados);
        model.put("resultadosTotal", resultadosTotal);
        model.put("variacaoMensal", variacaoMensal);

        return "home";
    }
}
