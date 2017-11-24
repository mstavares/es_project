package pt.ulusofona.es.g6.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pt.ulusofona.es.g6.data.Despesa;

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
public class TesteDespesaController {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testeListarDespesas() throws Exception {
        confirmaDespesas(new ArrayList<>());
    }

    @Test
    public void testeInserirDespesa() throws Exception {
        Despesa despesa = getDespesa();
        despesa.setId(1);
        inserirDespesa(despesa);
    }

    @Test
    public void testeAlterarDespesa() throws Exception {
        List<Despesa> despesasEsperadasAlteradas = new ArrayList<>();
        Despesa despesa = getDespesa();
        Despesa despesaAlterada = getDespesa();
        despesa.setId(1);
        despesaAlterada.setId(1);
        despesaAlterada.setCategoria("Propinas");

        inserirDespesa(despesa);
        despesasEsperadasAlteradas.add(despesaAlterada);

        mvc.perform(get("/despesa/consultar/alterar/1")
                .principal(getPrincipal()))
                .andExpect(status().isOk())
                .andExpect(view().name("inserirDespesa"))
                .andExpect(model().attribute("despesa", despesa));

        alterarDespesa(despesaAlterada);

        confirmaDespesas(despesasEsperadasAlteradas);
        confirmaDespesa(despesaAlterada);
    }

    @Test
    public void testeEliminarCategoria() throws Exception {
        Despesa despesa = getDespesa();
        despesa.setId(1);
        inserirDespesa(despesa);

        mvc.perform(post("/despesa/consultar/eliminar/1")
                .principal(getPrincipal()))
                .andExpect(status().isOk())
                .andExpect(view().name("consultarDespesa"))
                .andExpect(model().attribute("despesas", new ArrayList<>()))
                .andExpect(model().attribute("message", DespesaController.MSG_DESPESA_ELIMINADA));
    }

    private Despesa getDespesa() {
        return new Despesa("Alimentacao", 50.0, "McDonalds", "Campo Grande","user1");
    }

    private void inserirDespesa(Despesa despesa) throws Exception {
        List<Despesa> despesasEsperadas = new ArrayList<>();
        despesasEsperadas.add(despesa);

        mvc.perform(post("/despesa/inserir")
                .principal(getPrincipal())
                .param("categoria", despesa.getCategoria())
                .param("valor", String.valueOf(despesa.getValor()))
                .param("descricao", despesa.getDescricao())
                .param("localizacao", despesa.getLocalizacao()))
                .andExpect(status().isOk())
                .andExpect(view().name("inserirDespesa"))
                .andExpect(model().attribute("message", DespesaController.MSG_DESPESA_ADICIONADA));

        confirmaDespesas(despesasEsperadas);
        confirmaDespesa(despesa);
    }

    private void alterarDespesa(Despesa despesa) throws Exception {
        mvc.perform(post("/despesa/inserir")
                .principal(getPrincipal())
                .param("id", String.valueOf(despesa.getId()))
                .param("categoria", despesa.getCategoria())
                .param("valor", String.valueOf(despesa.getValor()))
                .param("descricao", despesa.getDescricao())
                .param("localizacao", despesa.getLocalizacao()))
                .andExpect(status().isOk())
                .andExpect(view().name("inserirDespesa"))
                .andExpect(model().attribute("message", DespesaController.MSG_DESPESA_ALTERADA));
    }

    private void confirmaDespesas(List<Despesa> despesasEsperadas) throws Exception {
        mvc.perform(get("/despesa/consultar")
                .principal(getPrincipal()))
                .andExpect(status().isOk())
                .andExpect(view().name("consultarDespesa"))
                .andExpect(model().attribute("despesas", despesasEsperadas));
    }

    private void confirmaDespesa(Despesa despesa) throws Exception {
        mvc.perform(get("/despesa/consultar/alterar/1")
                .principal(getPrincipal()))
                .andExpect(status().isOk())
                .andExpect(view().name("inserirDespesa"))
                .andExpect(model().attribute("despesa", despesa));
    }

    private Principal getPrincipal() {
        return () -> "admin";
    }
}
