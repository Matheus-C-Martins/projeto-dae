package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="PAGAMENTOS")
@NamedQueries({
        @NamedQuery(
                name = "getAllPagamentos",
                query = "SELECT p FROM Pagamento p ORDER BY p.estado"
        )
})
public class Pagamento implements Serializable{

    @Id
    private String username;

    @Id
    private String tipoProduto;

    @NotNull
    private double quantidade;

    @NotNull
    private double preco;

    @NotNull
    private String estado;

    @NotNull
    private boolean recibo;

    public Pagamento() {
        super();
    }

    public Pagamento(String username, String tipoProduto, double quantidade, double preco, String estado) {
        this.username = username;
        this.tipoProduto = tipoProduto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.estado = estado;
    }

    public String getUsername() {
        return username;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public String getEstado() {
        return estado;
    }

    public boolean isRecibo() {
        return recibo;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setRecibo(boolean recibo) {
        this.recibo = recibo;
    }
}
