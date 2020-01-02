package ejbs;

import dtos.ModalidadeDTO;
import entities.*;
import ws.ModalidadePK;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Stateless(name = "ModalidadeEJB")
public class ModalidadeBean {
    @PersistenceContext
    protected EntityManager entityManager;

    public void create(String nome, String escalao){
        try {
            Modalidade modalidade = entityManager.find(Modalidade.class, new ModalidadePK(nome, escalao));
            if (modalidade != null) {
                throw new EJBException("JA EXISTE UM ATLETA COM ESSE USERNAME");
            }
            entityManager.persist(new Modalidade(nome, escalao));
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

    public Modalidade findModalidade(String nome, String escalao) {
        try{
            return entityManager.find(Modalidade.class, new ModalidadePK(nome, escalao));
        } catch (Exception e) {
            throw new EJBException("ERRO_A_ENCONTRAR_MODALIDADE", e);
        }
    }

    public void removeModalidade(String nome, String escalao){
        try{
            Modalidade modalidade = findModalidade(nome, escalao);
            if(modalidade != null){
                entityManager.remove(modalidade);
            } else {
                System.err.println("ERRO_A_ENCONTRAR_MODALIDADE " + nome);
            }
        } catch (Exception e){
            System.err.println("ERRO_A_REMOVER_MODALIDADE ----> "+ e.toString());
        }
    }
}
