package ejbs;

import dtos.ModalidadeDTO;
import entities.Atleta;
import entities.Escaloes;
import entities.Modalidade;
import entities.Treinador;

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
    ModalidadeDTO modalidadeDTO;


    public void create(String nome, Set<Treinador> treinadores, Set<Escaloes> escaloes, Set<Atleta> atletas){
        try {
            Modalidade a = new Modalidade(nome, treinadores, atletas, escaloes);
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

    public Modalidade findModalidade(String nome) {
        try{
            return entityManager.find(Modalidade.class, nome);
        } catch (Exception e) {
            throw new EJBException("ERRO_A_ENCONTRAR_MODALIDADE", e);
        }
    }

    public void updateModalidade(String nome, Set<Treinador> treinadores, Set<Escaloes> escaloes, Set<Atleta> atletas){
        try{

            Modalidade mod = findModalidade(nome);
            if(mod != null){
                entityManager.lock(mod, LockModeType.OPTIMISTIC);
                mod.setNome(nome);
                mod.setTreinadores(treinadores);
                mod.setEscaloes(escaloes);
                mod.setAtletas(atletas);
            } else {
                System.out.println("ERRO_A_ENCONTRAR_MODALIDADE " + nome);
            }
        } catch (Exception e){
            throw new EJBException("ERRO_A_ATUALIZAR_MODALIDADE", e);
        }
    }

    public void removeModalidade(String nome){
        try{
            Modalidade modalidade = findModalidade(nome);
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
