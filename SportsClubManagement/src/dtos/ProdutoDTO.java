package dtos;

import java.io.Serializable;

public class ProdutoDTO implements Serializable {
    public String tipo, descrição;
    public double valorBase;

    public ProdutoDTO() {}

    public ProdutoDTO(String tipo, String descrição, double valorBase) {
        this.tipo = tipo;
        this.valorBase = valorBase;
        this.descrição = descrição;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }
}
