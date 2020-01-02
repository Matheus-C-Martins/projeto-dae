package entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity
@NamedQueries({
    @NamedQuery(
        name="getAllSocios",
        query = "SELECT s FROM Socio s ORDER BY s.username"
    )
})
public class Socio extends User implements Serializable {
    //Mensagens
    //Graduacoes
    //Pagamentos
    //Recibos

    public Socio() {
        super();
    }

    public Socio(String username, String password, String nome, String email) {
        super(username, password, nome, email);
    }
}