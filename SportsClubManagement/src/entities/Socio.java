package entities;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
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