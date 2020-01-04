package dtos;

import java.util.HashSet;
import java.util.Set;

public class TreinadorDTO {
    private String username, nome, password, email;
    private Set<ModalidadeDTO> modalidades;

    public TreinadorDTO() {
        this.modalidades = new HashSet<>();
    }

    public TreinadorDTO(String username, String nome, String password, String email, Set<ModalidadeDTO> modalidades) {
        this.username = username;
        this.nome = nome;
        this.password = password;
        this.email = email;
        this.modalidades = modalidades;
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

    public Set<ModalidadeDTO> getModalidades() {
        return modalidades;
    }

    public void setModalidades(Set<ModalidadeDTO> modalidades) {
        this.modalidades = modalidades;
    }
}
