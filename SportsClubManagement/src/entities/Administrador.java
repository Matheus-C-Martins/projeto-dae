package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllAdministradores",
                query = "SELECT a FROM Administrador a ORDER BY a.username"
        )
})
public class Administrador extends User implements Serializable {
    public Administrador() {
        super();
    }

    public Administrador(String username, String password, String nome, String email) {
        super(username, password, nome, email);
    }
}