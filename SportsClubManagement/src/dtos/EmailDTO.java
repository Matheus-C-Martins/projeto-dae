package dtos;

public class EmailDTO {
    private String assunto, mensagem;

    public EmailDTO() {
    }

    public EmailDTO(String assunto, String mensagem) {
        this.assunto = assunto;
        this.mensagem = mensagem;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
