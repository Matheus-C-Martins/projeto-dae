package ejbs;

import entities.Atleta;
import entities.Escaloes;
import entities.Modalidade;
import entities.Treinador;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

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
            throw new EJBException("ERRO_A_RECEBER_MODALIDADES", e);
        }
    }

    public Modalidade findModalidade(String nome, Set<Treinador> treinadores, Set<Escaloes> escaloes, Set<Atleta> atletas) {
        try{
            return entityManager.find(Modalidade.class, nome);
        } catch (Exception e) {
            throw new EJBException("ERRO_A_ENCONTRAR_MODALIDADE", e);
        }
    }
}
