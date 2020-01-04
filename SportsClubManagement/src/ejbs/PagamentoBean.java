package ejbs;

import dtos.AdministradorDTO;
import entities.Pagamento;
import entities.PagamentoPK;
import entities.Produto;
import entities.User;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "PagamentoEJB")
public class PagamentoBean {
    @PersistenceContext
    protected EntityManager entityManager;

    private ProdutoBean produtoBean = null;
    private AdministradorBean administradorBean = null;
    private SocioBean socioBean = null;
    private TreinadorBean treinadorBean = null;
    private AtletaBean atletaBean = null;

    public void create(String username, String tipoProduto, double quantidade, double preco, String estado) {
        try {
            if (produtoBean.findProduto(tipoProduto) != null) {
                if(administradorBean.findAdmin(username)!=null || socioBean.findSocio(username)!=null || treinadorBean.findTreinador(username)!=null || atletaBean.findAtleta(username)!=null) {
                    Pagamento pagamento = entityManager.find(Pagamento.class, new PagamentoPK(username, tipoProduto));
                    if (pagamento != null) {
                        throw new EJBException("JA EXISTE UM UTILIZADOR COM ESSE PRODUTO");
                    }
                    entityManager.persist(new Pagamento(username, tipoProduto, quantidade, preco, estado));
                }
            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<Pagamento> all() {
        try {
            return (List<Pagamento>) entityManager.createNamedQuery("getAllPagamentos").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERRO A RECEBER PAGAMENTOS", e);
        }
    }

    public Pagamento findPagamento(String username, String tipoProduto) {
        try {
            if(produtoBean.findProduto(tipoProduto)!=null){
                if (administradorBean.findAdmin(username) != null || socioBean.findSocio(username) != null || treinadorBean.findTreinador(username) != null || atletaBean.findAtleta(username) != null) {
                    return entityManager.find(Pagamento.class, new PagamentoPK(username, tipoProduto));
                }
            }
            } catch (Exception e) {
                 throw new EJBException("ERRO A ENCONTRAR PAGAMENTO", e);
            }
        return null;
    }

    public void updatePagamento(String username, String tipoProduto, double quantidade, double preco, String estado) {
        try{
            if (produtoBean.findProduto(tipoProduto) != null) {
                if (administradorBean.findAdmin(username) != null || socioBean.findSocio(username) != null || treinadorBean.findTreinador(username) != null || atletaBean.findAtleta(username) != null) {
                    Pagamento pagamento = findPagamento(username, tipoProduto);
                    if (pagamento != null) {
                        entityManager.lock(pagamento, LockModeType.OPTIMISTIC);
                        pagamento.setQuantidade(quantidade);
                        pagamento.setPreco(preco);
                        pagamento.setEstado(estado);
                    } else {
                        System.err.println("ERRO A ENCONTRAR PAGAMENTO");
                    }
                }
            }
        } catch (Exception e){
            System.err.println("ERRO A ATUALIZAR PAGAMENTO ("+username + tipoProduto+") ----> "+ e.toString());
        }
    }

    public void removePagamento(String username, String tipoProduto){
        try{
            if (produtoBean.findProduto(tipoProduto) != null) {
                if (administradorBean.findAdmin(username) != null || socioBean.findSocio(username) != null || treinadorBean.findTreinador(username) != null || atletaBean.findAtleta(username) != null) {

                    Pagamento pagamento = findPagamento(username, tipoProduto);
                    if (pagamento != null) {
                        entityManager.remove(pagamento);
                    } else {
                        System.err.println("ERRO A ENCONTRAR PAGAMENTO ");
                    }
                }
            }
        } catch (Exception e){
            System.err.println("ERRO A REMOVER PAGAMENTO ----> "+ e.toString());
        }
    }
}
