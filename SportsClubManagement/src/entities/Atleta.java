package entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Atleta extends Socio implements Serializable {
    @NotNull
    @ManyToMany
    @JoinColumn(name="ATLETA_USERNAME")
    private Set<Modalidade> modalidades;
    // Horarios

    public Atleta() {
        super();
        this.modalidades =  new HashSet<>();
    }

    public Atleta(String username, String password, String nome, String email) {
        super(username, password, nome, email);
        this.modalidades =  new HashSet<>();
    }
}
