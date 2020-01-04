package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="HORARIOS")
@IdClass(HorarioPK.class)
@NamedQueries({
        @NamedQuery(
                name = "getAllHorarios",
                query = "SELECT h FROM Horario h ORDER BY h.diaSemana, h.horaInicio, h.horaFim"
        )
})
public class Horario {
    @Id
    private String diaSemana;
    @Id
    private String horaInicio;
    @Id
    private String horaFim;

    @NotNull
    @ManyToMany
    @JoinTable(name = "HORARIO_MODALIDADES", joinColumns = {
            @JoinColumn(name = "DIA_SEMANA", referencedColumnName = "DIASEMANA"),
            @JoinColumn(name = "HORA_INICIO", referencedColumnName = "HORAINICIO"),
            @JoinColumn(name = "HORA_FIM", referencedColumnName = "HORAFIM")},
            inverseJoinColumns = {
                    @JoinColumn(name = "NOME_MODALIDADE", referencedColumnName ="NOME"),
                    @JoinColumn(name = "ESCALAO_MODALIDADE", referencedColumnName ="ESCALAO"),
            })
    private Set<Modalidade> modalidades;

    public Horario() {
        this.modalidades = new HashSet<>();
    }

    public Horario(String diaSemana, String horaInicio, String horaFim) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.modalidades = new HashSet<>();
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

    public Set<Modalidade> getModalidades() {
        return modalidades;
    }

    public void setModalidades(Set<Modalidade> modalidades) {
        this.modalidades = modalidades;
    }

    public void addModalidade(Modalidade modalidade){
        modalidades.add(modalidade);
    }

    public void removeModalidade(Modalidade modalidade){
        modalidades.remove(modalidade);
    }

    public void removeAllModalidade(){
        modalidades = new HashSet<>();
    }
}
