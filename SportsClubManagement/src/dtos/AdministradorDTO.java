package dtos;

import java.io.Serializable;

public class AdministradorDTO implements Serializable {
    public String username, password, nome, email;

    public AdministradorDTO() {
    }

    public AdministradorDTO(String username, String password, String nome, String email) {
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
