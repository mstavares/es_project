package pt.ulusofona.es.g6.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Categoria implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String categoria;

    public Categoria() {}

    public Categoria(String categoria) {
        this.categoria = categoria;
    }

    public Categoria(String categoria, long id) {
        this.categoria = categoria;
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Categoria categoria1 = (Categoria) o;

        if (id != categoria1.id) return false;
        return categoria != null ? categoria.equals(categoria1.categoria) : categoria1.categoria == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (categoria != null ? categoria.hashCode() : 0);
        return result;
    }

}
