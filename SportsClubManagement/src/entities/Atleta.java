package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAtletas",
                query = "SELECT a FROM Atleta a ORDER BY a.username"
        )
})
public class Atleta extends Socio implements Serializable {
    @NotNull
    @ManyToMany
    @JoinTable(name = "ATLETA_MODALIDADES", joinColumns = {
            @JoinColumn(name = "ATLETA_USERNAME", referencedColumnName = "USERNAME")},
            inverseJoinColumns = {
                    @JoinColumn(name = "NOME_MODALIDADE", referencedColumnName ="NOME"),
                    @JoinColumn(name = "ESCALAO_MODALIDADE", referencedColumnName ="ESCALAO"),
            })
    private Set<Modalidade> modalidades;

    public Atleta() {
        super();
        this.modalidades =  new HashSet<>();
    }

    public Atleta(String username, String password, String nome, String email) {
        super(username, password, nome, email);
        this.modalidades =  new HashSet<>();
    }

    public Set<Modalidade> getModalidades() {
        return modalidades;
    }

    public void setModalidades(Set<Modalidade> modalidades) {
        this.modalidades = modalidades;
    }

    public void addModalidade(Modalidade modalidade){
        modalidades.add(modalidade);
    }

    public void removeModalidade(Modalidade modalidade){
        modalidades.remove(modalidade);
    }

    public void removeAllModalidade(){
        modalidades = new HashSet<>();
    }
}
