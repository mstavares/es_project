package pt.ulusofona.es.g6.form;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DespesaForm {

    private Long id;

    @NotEmpty(message = "O campo da categoria não pode ficar vazio")
    private String categoria;

    @Size(max = 160, message = "O campo da descrição só aceita até 160 caracteres")
    @NotEmpty(message = "O campo da descrição não pode ficar vazio")
    private String descricao;

    @NotNull(message = "O campo do valor nao pode ficar vazio")
    @Min(value = 0, message = "O valor da despesa tem de ser superior a 0")
    private Double valor;

    private String localizacao;

    public Long getId() {
        return id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getValor() {
        return valor;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
