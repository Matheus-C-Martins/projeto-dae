package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllTreinadores",
                query = "SELECT t FROM Treinador t ORDER BY t.username"
        )
})
public class Treinador extends Socio implements Serializable {
    @NotNull
    @ManyToMany
    @JoinTable(name = "TREINADOR_MODALIDADES", joinColumns = {
            @JoinColumn(name = "TREINADOR_USERNAME", referencedColumnName = "USERNAME")},
            inverseJoinColumns = {
                @JoinColumn(name = "NOME_MODALIDADE", referencedColumnName ="NOME"),
                @JoinColumn(name = "ESCALAO_MODALIDADE", referencedColumnName ="ESCALAO"),
            })
    private Set<Modalidade> modalidades;

    public Treinador() {
        super();
        this.modalidades =  new HashSet<>();
    }

    public Treinador(String username, String password, String nome, String email) {
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
