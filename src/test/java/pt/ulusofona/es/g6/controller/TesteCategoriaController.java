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
import pt.ulusofona.es.g6.data.Categoria;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/dispatcher-servlet-test.xml")
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TesteCategoriaController {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testeListarCategorias() throws Exception {
        confirmaCategorias(new ArrayList<>());
    }

    @Test
    public void testeInserirCategoria() throws Exception {
        String categoriaAInserir = "Carro";
        Categoria categoria = new Categoria(categoriaAInserir, 1);

        inserirCategoria(categoria);

        List<Categoria> categoriasEsperadas = new ArrayList<>();
        categoriasEsperadas.add(categoria);

        confirmaCategorias(categoriasEsperadas);
        confirmaCategoria(categoriasEsperadas.get(0));
    }

    @Test
    public void testeAlterarCategoria() throws Exception {
        String categoriaAInserir = "Carro";
        String nomeDaCategoriaAlterada = "Automovel";
        Categoria categoria = new Categoria(categoriaAInserir, 1);
        Categoria categoriaAlterada = new Categoria(nomeDaCategoriaAlterada, 1);
        List<Categoria> categoriasEsperadas = new ArrayList<>();

        inserirCategoria(categoria);
        categoriasEsperadas.add(categoria);

        confirmaCategorias(categoriasEsperadas);
        confirmaCategoria(categoria);

        mvc.perform(post("/categoria/inserir")
                .param("id", String.valueOf(categoriaAlterada.getId()))
                .param("categoria", categoriaAlterada.getCategoria()))
                .andExpect(status().isOk())
                .andExpect(view().name("inserirCategoria"))
                .andExpect(model().attribute("message", CategoriaController.MSG_CATEGORIA_ALTERADA));

        confirmaCategoria(categoriaAlterada);
    }

    @Test
    public void testeEliminarCategoria() throws Exception {
        String categoriaAInserir = "Carro";
        inserirCategoria(new Categoria(categoriaAInserir, 1));

        mvc.perform(get("/categoria/consultar/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("consultarCategorias"))
                .andExpect(model().attribute("categorias", new ArrayList<>()))
                .andExpect(model().attribute("message", CategoriaController.MSG_CATEGORIA_ELIMINADA));
    }

    @Test
    public void testeAlterarCategoriaPredefinida() throws Exception {
        String categoriaPredefinida = "Propinas";
        inserirCategoria(new Categoria(categoriaPredefinida));

        mvc.perform(get("/categoria/consultar/alterar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("consultarCategorias"))
                .andExpect(model().attribute("messageError", CategoriaController.MSG_ALTERAR_CATEGORIA_PREDEF));
    }

    @Test
    public void testeEliminarCategoriaPredefinida() throws Exception {
        String categoriaPredefinida = "Propinas";
        inserirCategoria(new Categoria(categoriaPredefinida));

        mvc.perform(get("/categoria/consultar/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("consultarCategorias"))
                .andExpect(model().attribute("messageError", CategoriaController.MSG_ELIMINAR_CATEGORIA_PREDEF));
    }

    private void inserirCategoria(Categoria categoria) throws Exception {
        mvc.perform(post("/categoria/inserir")
                .param("categoria", categoria.getCategoria()))
                .andExpect(status().isOk())
                .andExpect(view().name("inserirCategoria"))
                .andExpect(model().attribute("message", CategoriaController.MSG_CATEGORIA_ADICIONADA));
    }

    private void confirmaCategorias(List<Categoria> categoriasEsperadas) throws Exception {
        mvc.perform(get("/categoria/consultar"))
                .andExpect(status().isOk())
                .andExpect(view().name("consultarCategorias"))
                .andExpect(model().attribute("categorias", categoriasEsperadas));
    }

    private void confirmaCategoria(Categoria categoria) throws Exception {
        mvc.perform(get("/categoria/consultar/alterar/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("inserirCategoria"))
                .andExpect(model().attribute("categoria", categoria));
    }
}
