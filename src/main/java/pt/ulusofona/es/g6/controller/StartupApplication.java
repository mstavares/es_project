package pt.ulusofona.es.g6.controller;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.ulusofona.es.g6.data.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.util.List;


@Component
@Transactional
public class StartupApplication implements SmartInitializingSingleton {

    private static boolean startup = true;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void afterSingletonsInstantiated() {

        if(startup) {
            List<Categoria> categorias = em.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();
            List<Despesa> despesas = em.createQuery("select c FROM Despesa c", Despesa.class).getResultList();
            List<AgregadoFamiliar> agregado = em.createQuery("select c FROM AgregadoFamiliar c", AgregadoFamiliar.class).getResultList();
            if(categorias.size() == 0 && despesas.size() == 0 && agregado.size() == 0) {
                /** Usado para popular a BD quando esta vazía */
                carregarCategoriasNaBD();
                carregarDespesaInicialNaBD("05/10/2016 16:01");
                carregaAgregadoFamilarNaBd();
                startup = false;
            }
        }
    }

    private void carregarCategoriasNaBD() {
        for(String categoria : StartUpUtils.getCategoriasPredefinidas()) {
            em.persist(new Categoria(categoria));
        }
    }

    private void carregarDespesaInicialNaBD(String data) {
        try {
            Despesa despesa = new Despesa();
            despesa.setCategoria("Renda");
            despesa.setDescricao("Campo Grande");
            despesa.setValor(500.0);
            despesa.setData(DataUtils.stringToDate(data));
            despesa.setUtilizador("user1");
            em.persist(despesa);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void carregaAgregadoFamilarNaBd(){
        for(String[] utilizadores : StartUpUtils.getAgregadoFamiliar()) {
            em.persist(new AgregadoFamiliar(utilizadores));
        }
    }

}
