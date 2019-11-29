package dtos;

import java.io.Serializable;

public class ModalidadeDTO implements Serializable {
    public String nome;

    public ModalidadeDTO() {
    }

    public ModalidadeDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
