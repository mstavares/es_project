package pt.ulusofona.es.g6.controller;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pt.ulusofona.es.g6.data.Categoria;
import pt.ulusofona.es.g6.data.Despesa;
import pt.ulusofona.es.g6.data.StartUpUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/dispatcher-servlet-test.xml")
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TesteMapaController {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testeInserirDespesa() throws Exception {
        List<Categoria> categorias = new ArrayList<>();
        List<Despesa> despesas = new ArrayList<>();

        despesas.add(new Despesa("Alimentacao", 50.0, "McDonalds", "Campo Grande","user1"));
        despesas.add(new Despesa("Alimentacao", 50.0, "Restaurante Chines", "Campo Grande","user2"));
        despesas.add(new Despesa("Renda", 500.0, "Casa", "Lisboa","user3"));

        double[][] resultados = new double[13][StartUpUtils.getCategoriasPredefinidas().length + 1];
        double[] resultadosTotal = new double[StartUpUtils.getCategoriasPredefinidas().length + 1];
        String[] variacaoMensal = new String[13];

        for(int i = 0; i < StartUpUtils.getCategoriasPredefinidas().length; i++) {
            Categoria categoria = new Categoria(StartUpUtils.getCategoriasPredefinidas()[i], i + 1);
            categorias.add(categoria);
            insereCategoria(categoria);
        }

        for(Despesa despesa : despesas){
            insereDespesa(despesa);
            for(int i = 0; i< StartUpUtils.getCategoriasPredefinidas().length - 1; i++){
                if(StartUpUtils.getCategoriasPredefinidas()[i].equals(despesa.getCategoria())) {
                    resultados[despesa.getData().getMonth() + 1][i] += despesa.getValor();
                    //----- Calcula totais direita -----
                    resultados[despesa.getData().getMonth() + 1][StartUpUtils.getCategoriasPredefinidas().length] += despesa.getValor();
                    //----- Calcula totais inferiores -----
                    resultadosTotal[i] += despesa.getValor();
                    //----- Calcula soma dos totais -----
                    resultadosTotal[categorias.size()] += despesa.getValor();
                    break;
                }
            }
        }

        //----- Calcula variacoes -----
        for(int i = 1; i < 13; i++) {
            double mesAnterior = resultados[i-1][categorias.size()];
            double mesActual = resultados[i][categorias.size()];

            if(mesAnterior == 0){
                variacaoMensal[i] = "-";
            } else {
                Double variacao = ((mesActual-mesAnterior) / mesAnterior) * 100;
                variacaoMensal[i] = String.valueOf(variacao.intValue()) + "%";
            }

        }

        MvcResult result = mvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("categorias", categorias))
                .andExpect(MockMvcResultMatchers.model().attributeExists("resultados"))
                .andExpect(model().attribute("resultadosTotal", resultadosTotal))
                .andExpect(model().attribute("variacaoMensal", variacaoMensal))
                .andReturn();

        Assert.assertArrayEquals((double[][])result.getModelAndView().getModel().get("resultados"), resultados);
    }

    private void insereDespesa(Despesa despesa) throws Exception {
        mvc.perform(post("/despesa/inserir")
                .principal(getPrincipal())
                .param("categoria", despesa.getCategoria())
                .param("valor", String.valueOf(despesa.getValor()))
                .param("descricao", despesa.getDescricao())
                .param("localizacao", despesa.getLocalizacao()))
                .andExpect(status().isOk())
                .andExpect(view().name("inserirDespesa"))
                .andExpect(model().attribute("message", DespesaController.MSG_DESPESA_ADICIONADA));
    }

    private void insereCategoria(Categoria categoria) throws Exception {
        mvc.perform(post("/categoria/inserir")
                .param("categoria", categoria.getCategoria()))
                .andExpect(status().isOk())
                .andExpect(view().name("inserirCategoria"))
                .andExpect(model().attribute("message", CategoriaController.MSG_CATEGORIA_ADICIONADA));
    }

    private Principal getPrincipal() {
        return () -> "admin";
    }

}