package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="PRODUTOS")
@NamedQueries({
        @NamedQuery(
                name="getAllProdutos",
                query = "SELECT p FROM Produto p ORDER BY p.tipo"
        )
})
public class Produto implements Serializable {
    @Id
    private String tipo;
    @NotNull
    private String descricao;
    @NotNull
    private double valorBase;

    public Produto() {
    }

    public Produto(String tipo, String descricao, double valorBase) {
        this.descricao = descricao;
        this.valorBase = valorBase;
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
