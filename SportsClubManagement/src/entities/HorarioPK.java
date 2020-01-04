package entities;

import java.io.Serializable;
import java.util.Objects;

public class HorarioPK implements Serializable {
    private String diaSemana, horaInicio, horaFim;

    public HorarioPK() {
    }

    public HorarioPK(String diaSemana, String horaInicio, String horaFim) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HorarioPK horarioPK = (HorarioPK) o;
        return Objects.equals(diaSemana, horarioPK.diaSemana) &&
                Objects.equals(horaInicio, horarioPK.horaInicio) &&
                Objects.equals(horaFim, horarioPK.horaFim);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diaSemana, horaInicio, horaFim);
    }
}
