package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllModalidades",
                query = "SELECT m FROM Modalidade m ORDER BY m.nome"
        )
})
@Table(name="MODALIDADES")
public class Modalidade implements Serializable {
    @Id
    private String nome;

    @NotNull
    @OneToMany
    private Set<Treinador> treinadores;

    @NotNull
    @ManyToMany
    private Set<Atleta> atletas;

    @NotNull
    @ElementCollection
    private Set<Escaloes> escaloes;

    /*@NotNull
    private Date horario;*/ // TEM DE SER DA CLASS HORARIO, QUE VAI TER UMA HORA DE INICIO, HORA DE FIM E UM DIA DA SEMANDA

    public Modalidade() {
        this.treinadores = new HashSet<>();
        this.atletas = new HashSet<>();
        this.escaloes = new HashSet<>();
    }

    public Modalidade(String nome) {
        this.nome = nome;
        this.treinadores = new HashSet<>();
        this.atletas = new HashSet<>();
        this.escaloes = new HashSet<>();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
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

    public void adicionarEscalao(Escaloes escalao){
        escaloes.add(escalao);
    }

    public void removerEscalao(Escaloes escalao){
        escaloes.remove(escalao);
    }
}
