package pt.ulusofona.es.g6.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Despesa implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false, length = 160)
    private String descricao;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private String utilizador;

    private String localizacao;

    private Date data;

    public Despesa() {}

    public Despesa(String categoria, Double valor, String descricao, String localizacao, String utilizador) {
        this(categoria, valor, descricao, localizacao, new Timestamp(Calendar.getInstance().getTimeInMillis()), utilizador);
    }

    public Despesa(String categoria, Double valor, String descricao, String localizacao, Date data, String utilizador) {
        this.categoria = categoria;
        this.valor = valor;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.data = data;
        this.utilizador = utilizador;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(String utilizador) {
        this.utilizador = utilizador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Despesa despesa = (Despesa) o;

        if (id != despesa.id) return false;
        if (categoria != null ? !categoria.equals(despesa.categoria) : despesa.categoria != null) return false;
        if (descricao != null ? !descricao.equals(despesa.descricao) : despesa.descricao != null) return false;
        if (valor != null ? !valor.equals(despesa.valor) : despesa.valor != null) return false;
        return localizacao != null ? localizacao.equals(despesa.localizacao) : despesa.localizacao == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (categoria != null ? categoria.hashCode() : 0);
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        result = 31 * result + (valor != null ? valor.hashCode() : 0);
        result = 31 * result + (localizacao != null ? localizacao.hashCode() : 0);
        return result;
    }
}
