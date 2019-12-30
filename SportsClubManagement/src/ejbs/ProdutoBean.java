package ejbs;

import entities.Produto;
import entities.Socio;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "ProdutoEJB")
public class ProdutoBean {
    @PersistenceContext
    protected EntityManager entityManager;

    public void create(String tipo, String descricao, double valorBase) {
        try {
            Produto produto = entityManager.find(Produto.class, tipo);
            if (produto != null) {
                throw new EJBException("JA EXISTE UM PRODUTO COM ESSE TIPO");
            }
            entityManager.persist(new Produto(tipo, descricao, valorBase));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<Produto> all() {
        try {
            return (List<Produto>) entityManager.createNamedQuery("getAllProdutos").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERRO A RECEBER PRODUTOS", e);
        }
    }

    public Produto findProduto(String tipo) {
        try {
            return entityManager.find(Produto.class, tipo);
        } catch (Exception e) {
            throw new EJBException("ERRO A ENCONTRAR PRODUTO ("+tipo+")", e);
        }
    }

    public void updateProduto(String tipo, String descricao, double valorBase) {
        try{
            Produto produto = findProduto(tipo);
            if(produto != null){
                entityManager.lock(produto, LockModeType.OPTIMISTIC);
                produto.setDescricao(descricao);
                produto.setValorBase(valorBase);
            } else {
                System.err.println("ERRO A ENCONTRAR PRODUTO");
            }
        } catch (Exception e){
            System.err.println("ERRO A ATUALIZAR PRODUTO ("+tipo+") ----> "+ e.toString());
        }
    }

    public void removeProduto(String tipo) {
        try {
            Produto produto = findProduto(tipo);
            if (produto == null) {
                throw new EJBException("O PRODUTO COM O TIPO " + tipo + " NAO FOI ENCONTRADO");
            }
            entityManager.remove(produto);
        } catch (Exception e) {
            System.err.println("ERRO A REMOVER TIPO ("+tipo+") ----> "+ e.toString());
        }
    }
}
