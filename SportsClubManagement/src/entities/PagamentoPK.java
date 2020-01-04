package entities;

import java.io.Serializable;
import java.util.Objects;

public class PagamentoPK implements Serializable {
    private String utilizador, produto;

    public PagamentoPK() {
    }

    public PagamentoPK(String utilizador, String produto) {
        this.utilizador = utilizador;
        this.produto = produto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PagamentoPK that = (PagamentoPK) o;
        return Objects.equals(utilizador, that.utilizador) &&
                Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utilizador, produto);
    }
}
