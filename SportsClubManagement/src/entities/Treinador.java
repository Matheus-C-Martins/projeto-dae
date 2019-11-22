package entities;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Treinador extends Socio implements Serializable {
    // Set<Modalidade> modalidades;
    // Horarios

    public Treinador() {
        super();
    }

    public Treinador(String username, String password, String nome, String email) {
        super(username, password, nome, email);
    }
}
