package ejbs;

import entities.Administrador;
import entities.Modalidade;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "ModalidadeEJB")
public class ModalidadeBean {
    @PersistenceContext
    protected EntityManager entityManager;

    public void create(String nome){
        try {
            Modalidade a = new Modalidade(nome);
            entityManager.persist(a);
        }catch (Exception e){
            throw new EJBException(e);
        }
    }

    public List<Modalidade> all() {
        try {
            return (List<Modalidade>) entityManager.createNamedQuery("getAllModalidades").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR_RETRIEVING_MODALIDADES", e);
        }
    }
}
