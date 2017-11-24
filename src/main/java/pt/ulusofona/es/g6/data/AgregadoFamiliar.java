package pt.ulusofona.es.g6.data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AgregadoFamiliar {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String[] utilizadores;

    public AgregadoFamiliar(){}

    public AgregadoFamiliar(String[] utilizadores){
        this.utilizadores = utilizadores;
    }


    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public void setUtilizadores(String[] utilizadores) {
        this.utilizadores = utilizadores;
    }
    public String[] getUtilizadores() {
        return utilizadores;
    }


}