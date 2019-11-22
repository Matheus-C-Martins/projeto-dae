package entities;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Adiministrador extends User implements Serializable {
    public Adiministrador() {
        super();
    }

    public Adiministrador(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}