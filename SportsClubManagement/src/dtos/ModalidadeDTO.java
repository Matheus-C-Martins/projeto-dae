package dtos;

import entities.Atleta;
import entities.Treinador;

import java.io.Serializable;
import java.util.Set;

public class ModalidadeDTO implements Serializable {
    public String nome, escalao;
    public Set<Treinador> treinadores;
    public Set<Atleta> atletas;

    public ModalidadeDTO() {
    }

    public ModalidadeDTO(String nome, String escalao) {
        this.nome = nome;
        this.escalao = escalao;
    }

    public ModalidadeDTO(String nome, Set<Treinador> treinadores, Set<Atleta> atletas, String escalao) {
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

    public Set<Treinador> getTreinadores() {
        return treinadores;
    }

    public void setTreinadores(Set<Treinador> treinadores) {
        this.treinadores = treinadores;
    }

    public Set<Atleta> getAtletas() {
        return atletas;
    }

    public void setAtletas(Set<Atleta> atletas) {
        this.atletas = atletas;
    }
}
