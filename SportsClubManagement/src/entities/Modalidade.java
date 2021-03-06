package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@IdClass(ModalidadePK.class)
@NamedQueries({
    @NamedQuery(
        name = "getAllModalidadeEscaloes",
        query = "SELECT m.escalao FROM Modalidade m WHERE UPPER(m.nome) = UPPER(:nome) ORDER BY m.escalao"
    ),
    @NamedQuery(
        name = "getAllModalidadesNomes",
        query = "SELECT DISTINCT m.nome FROM Modalidade m ORDER BY m.nome"
    )
})
@Table(name="MODALIDADES")
public class Modalidade implements Serializable {
    @Id
    private String nome;
    @Id
    private String escalao;

    @ManyToMany
    @JoinTable(name = "MODALIDADES_TREINADORES", joinColumns = {
            @JoinColumn(name = "NOME_MODALIDADE", referencedColumnName = "NOME"),
            @JoinColumn(name = "ESCALAO_MODALIDADE", referencedColumnName = "ESCALAO") },
            inverseJoinColumns = @JoinColumn(name = "TREINADOR_USERNAME", referencedColumnName ="USERNAME"))
    private Set<Treinador> treinadores;

    @ManyToMany
    @JoinTable(name = "MODALIDADES_ATLETAS", joinColumns = {
            @JoinColumn(name = "NOME_MODALIDADE", referencedColumnName = "NOME"),
            @JoinColumn(name = "ESCALAO_MODALIDADE", referencedColumnName = "ESCALAO") },
            inverseJoinColumns = @JoinColumn(name = "ATLETA_USERNAME", referencedColumnName ="USERNAME"))
    private Set<Atleta> atletas;

    @ManyToMany
    @JoinTable(name = "MODALIDADES_HORARIOS", joinColumns = {
            @JoinColumn(name = "NOME_MODALIDADE", referencedColumnName = "NOME"),
            @JoinColumn(name = "ESCALAO_MODALIDADE", referencedColumnName = "ESCALAO") },
            inverseJoinColumns = {
                @JoinColumn(name = "DIA_SEMANA", referencedColumnName = "DIASEMANA"),
                @JoinColumn(name = "HORA_INICIO", referencedColumnName = "HORAINICIO"),
                @JoinColumn(name = "HORA_FIM", referencedColumnName = "HORAFIM")
            })
    private Set<Horario> horarios;

    public Modalidade() {
        this.treinadores = new HashSet<>();
        this.atletas = new HashSet<>();
    }

    public Modalidade(String nome, String escalao) {
        this.nome = nome;
        this.escalao = escalao;
        this.treinadores = new HashSet<>();
        this.atletas = new HashSet<>();
        this.horarios = new HashSet<>();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
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

    public Set<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }

    public void adicionarTreinador(Treinador treinador){
        treinadores.add(treinador);
    }

    public void removerTreinador(Treinador treinador){
        treinadores.remove(treinador);
    }

    public void adicionarAtleta(Atleta atleta){
        atletas.add(atleta);
    }

    public void removerAtleta(Atleta atleta){
        atletas.remove(atleta);
    }

    public void adicionarHorario(Horario horario){
        horarios.add(horario);
    }

    public void removerHorario(Horario horario){
        horarios.remove(horario);
    }
}
