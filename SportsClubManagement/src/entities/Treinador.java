package entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Treinador extends Socio implements Serializable {
    /*@NotNull
    @ManyToOne
    @JoinColumn(name="TREINADOR_USERNAME")
    private Modalidade modalidade;
    // Horarios

    public Treinador() {
        super();
    }

    public Treinador(String username, String password, String nome, String email, Modalidade modalidade) {
        super(username, password, nome, email);
        this.modalidade =  modalidade;
    }*/
}
