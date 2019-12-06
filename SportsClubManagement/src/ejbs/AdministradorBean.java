package ejbs;

import entities.Administrador;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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

    public List<Administrador> all() {
        try {
            // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
            return (List<Administrador>) entityManager.createNamedQuery("getAllAdministradores").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_A_RECEBER_ADMINISTRADORES", e);
        }
    }

    public Administrador findAdmin(String username) {
        try{
            return entityManager.find(Administrador.class, username);
        } catch (Exception e) {
            throw new EJBException("ERRO_A_ENCONTRAR_ADMINISTRADOR", e);
        }
    }
}
