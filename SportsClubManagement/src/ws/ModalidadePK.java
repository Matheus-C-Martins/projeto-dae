package ws;

import java.io.Serializable;
import java.util.Objects;

public class ModalidadePK implements Serializable {
    private String nome, escalao;

    public ModalidadePK() {
    }

    public ModalidadePK(String nome, String escalao) {
        this.nome = nome;
        this.escalao = escalao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModalidadePK that = (ModalidadePK) o;
        return Objects.equals(nome, that.nome) &&
                Objects.equals(escalao, that.escalao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, escalao);
    }
}
