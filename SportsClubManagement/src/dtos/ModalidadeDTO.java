package dtos;

import entities.Atleta;
import entities.Escaloes;
import entities.Treinador;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ModalidadeDTO implements Serializable {
    public String nome;
    public Set<Treinador> treinadores;
    public Set<Atleta> atletas;
    public Set<Escaloes> escaloes;

    public ModalidadeDTO() {
    }

    public ModalidadeDTO(String nome) {
        this.nome = nome;
    }

    public ModalidadeDTO(String nome, Set<Treinador> treinadores, Set<Atleta> atletas, Set<Escaloes> escaloes) {
        this.nome = nome;
        this.treinadores = treinadores;
        this.atletas = atletas;
        this.escaloes = escaloes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Set<Escaloes> getEscaloes() {
        return escaloes;
    }

    public void setEscaloes(Set<Escaloes> escaloes) {
        this.escaloes = escaloes;
    }
}
