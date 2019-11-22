package entities;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Administrador extends User implements Serializable {
    public Administrador() {
        super();
    }

    public Administrador(String username, String password, String nome, String email) {
        super(username, password, nome, email);
    }
}