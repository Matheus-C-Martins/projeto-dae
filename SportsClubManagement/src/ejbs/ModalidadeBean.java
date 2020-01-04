package ejbs;

import entities.*;
import entities.ModalidadePK;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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

    public List<String> all() {
        try {
            return (List<String>) entityManager.createNamedQuery("getAllModalidadesNomes").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERRO A RECEBER MODALIDADES", e);
        }
    }

    public List<String> allModalidadeEscaloes(String nome) {
        try {
            return (List<String>) entityManager.createNamedQuery("getAllModalidadeEscaloes").setParameter("nome", nome).getResultList();
        } catch (Exception e) {
            throw new EJBException("ERRO A RECEBER ESCALOES DA MODALIDADE ("+nome+")", e);
        }
    }

    public Modalidade findModalidade(String nome, String escalao) {
        try{
            return entityManager.find(Modalidade.class, new ModalidadePK(nome, escalao));
        } catch (Exception e) {
            throw new EJBException("ERRO A ENCONTRAR MODALIDADE", e);
        }
    }

    public void removeModalidade(String nome, String escalao){
        try{
            Modalidade modalidade = findModalidade(nome, escalao);
            if(modalidade != null){
                entityManager.remove(modalidade);
            } else {
                System.err.println("ERRO A ENCONTRAR MODALIDADE " + nome);
            }
        } catch (Exception e){
            System.err.println("ERRO A REMOVER MODALIDADE ----> "+ e.toString());
        }
    }
}
