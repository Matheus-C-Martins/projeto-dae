package ejbs;

import entities.Administrador;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(name = "AdministradorEJB")
public class AdministradorBean {
    @PersistenceContext
    protected EntityManager entityManager;

    public void create(String username, String password, String name, String email){
        try {
            Administrador a = new Administrador(username, password, name, email);
            entityManager.persist(a);
        }catch (Exception e){
            throw new EJBException(e);
        }
    }
}
