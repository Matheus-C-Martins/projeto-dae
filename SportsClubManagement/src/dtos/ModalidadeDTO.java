package dtos;

import java.io.Serializable;
import java.util.Set;

public class ModalidadeDTO implements Serializable {
    public String nome, escalao;
    public Set<TreinadorDTO> treinadores;
    public Set<AtletaDTO> atletas;

    public ModalidadeDTO() {
    }

    public ModalidadeDTO(String nome) {
        this.nome = nome;
    }

    public ModalidadeDTO(String escalao, int flag) {
        this.escalao = escalao;
    }

    public ModalidadeDTO(String nome, String escalao) {
        this.nome = nome;
        this.escalao = escalao;
    }

    public ModalidadeDTO(String nome, Set<TreinadorDTO> treinadores, Set<AtletaDTO> atletas, String escalao) {
        this.nome = nome;
        this.treinadores = treinadores;
        this.atletas = atletas;
        this.escalao = escalao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEscalao() {
        return escalao;
    }

    public void setEscalao(String escalao) {
        this.escalao = escalao;
    }

    public Set<TreinadorDTO> getTreinadores() {
        return treinadores;
    }

    public void setTreinadores(Set<TreinadorDTO> treinadores) {
        this.treinadores = treinadores;
    }

    public Set<AtletaDTO> getAtletas() {
        return atletas;
    }

    public void setAtletas(Set<AtletaDTO> atletas) {
        this.atletas = atletas;
    }
}
