package dtos;

import entities.Produto;
import entities.User;

public class PagamentoDTO {

    public String username, tipoProduto, estado;
    public double quantidade, preco;
    public boolean recibo;

    public PagamentoDTO() {}

    public PagamentoDTO(String username, String tipoProduto, double quantidade, double preco, String estado) {
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
