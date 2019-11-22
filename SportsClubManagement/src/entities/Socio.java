package entities;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Socio extends User implements Serializable {

    //private Set<Modalidade> modalidades;

    public Socio() {
        super();
    }

    public Socio(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
