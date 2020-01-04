package dtos;

import java.io.Serializable;
import java.util.Set;

public class HorarioDTO implements Serializable {
    private String diaSemana, horaInicio, horaFim;
    private Set<ModalidadeDTO> modalidades;

    public HorarioDTO() {
    }

    public HorarioDTO(String diaSemana, String horaInicio, String horaFim, Set<ModalidadeDTO> modalidades) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.modalidades = modalidades;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public Set<ModalidadeDTO> getModalidades() {
        return modalidades;
    }

    public void setModalidades(Set<ModalidadeDTO> modalidades) {
        this.modalidades = modalidades;
    }
}
