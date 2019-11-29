package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Modalidade implements Serializable {
    @Id
    private String nome;
    @NotNull
    @ManyToMany
    private Set<Socio> socios;
    /*@NotNull
    @ManyToMany
    private Set<Escaloes> escaloes;*/
    @NotNull
    private Date horario;

    public Modalidade() {
        this.socios = new HashSet<>();
        //this.escaloes = new HashSet<>();
    }

    public Modalidade(String nome) {
        this.nome = nome;
        this.socios = new HashSet<>();
        //this.escaloes = new HashSet<>();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSocios(Set<Socio> socios) {
        this.socios = socios;
    }

    public String getNome() {
        return nome;
    }

    public Set<Socio> getSocios() {
        return socios;
    }

    /*public Set<Escaloes> getEscaloes() {
        return escaloes;
    }*/

    /*public void setEscaloes(Set<Escaloes> escaloes) {
        this.escaloes = escaloes;
    }*/

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }
}
