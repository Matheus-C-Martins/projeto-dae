package dtos;

import java.io.Serializable;
import java.util.Set;

public class ModalidadeDTO implements Serializable {
    private String nome, escalao;
    private Set<TreinadorDTO> treinadores;
    private Set<AtletaDTO> atletas;
    private Set<HorarioDTO> horarios;

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

    public ModalidadeDTO(String nome, Set<TreinadorDTO> treinadores, Set<AtletaDTO> atletas, String escalao,Set<HorarioDTO> horarios) {
        this.nome = nome;
        this.treinadores = treinadores;
        this.atletas = atletas;
        this.escalao = escalao;
        this.horarios = horarios;
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

    public Set<HorarioDTO> getHorarios() {
        return horarios;
    }

    public void setHorarios(Set<HorarioDTO> horarios) {
        this.horarios = horarios;
    }
}
