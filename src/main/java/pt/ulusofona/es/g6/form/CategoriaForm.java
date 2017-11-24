package pt.ulusofona.es.g6.form;


import org.hibernate.validator.constraints.NotEmpty;

public class CategoriaForm {

    private Long id;

    @NotEmpty(message = "O campo da categoria não pode ficar vazio")
    private String categoria;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
