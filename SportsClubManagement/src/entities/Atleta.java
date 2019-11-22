package entities;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Atleta extends Socio implements Serializable {
    // Set<Modalidade> modalidades;
    // Horarios

    public Atleta() {
        super();
    }

    public Atleta(String username, String password, String nome, String email) {
        super(username, password, nome, email);
    }
}
