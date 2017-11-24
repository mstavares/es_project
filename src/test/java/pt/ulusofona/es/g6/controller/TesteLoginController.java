package pt.ulusofona.es.g6.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/dispatcher-servlet-test.xml")
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TesteLoginController {


    @Resource
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithUserDetails("admin")
    public void testeAutenticaAdmin() throws Exception {
        mvc.perform(get("/home"))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @WithUserDetails("user1")
    public void testeAutenticaUser1() throws Exception {
        mvc.perform(get("/home"))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @WithUserDetails("user1")
    public void testeUserConsultarCategorias() throws Exception {
        mvc.perform(get("/categoria/consultar"))
                .andExpect(authenticated())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithUserDetails("admin")
    public void testeConsultarCategoriaAdmin() throws Exception {
        mvc.perform(get("/categoria/consultar"))
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("consultarCategorias"));
    }

}
