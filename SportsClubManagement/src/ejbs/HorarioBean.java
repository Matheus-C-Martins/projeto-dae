package ejbs;

import entities.*;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless(name = "HorarioEJB")
public class HorarioBean {
    @PersistenceContext
    protected EntityManager entityManager;

    public void create(String diaSemana, String horaInicio, String horaFim){
        try {
            Horario horario = entityManager.find(Horario.class, new HorarioPK(diaSemana, horaInicio, horaFim));
            if (horario != null) {
                throw new EJBException("JA EXISTE UM HORARIO COM ESSAS HORAS E DIA DA SEMANA");
            }
            entityManager.persist(new Horario(diaSemana, horaInicio, horaFim));
        }catch (Exception e){
            throw new EJBException(e);
        }
    }

    public List<Horario> all() {
        try {
            return (List<Horario>) entityManager.createNamedQuery("getAllHorarios").getResultList();
        } catch (Exception e) {
            throw new EJBException("ERROR A OBTER HORARIOS", e);
        }
    }

    public Horario findHorario(String diaSemana, String horaInicio, String horaFim) {
        try{
            return entityManager.find(Horario.class, new HorarioPK(diaSemana, horaInicio, horaFim));
        } catch (Exception e) {
            throw new EJBException("ERRO A ENCONTRAR HORARIO ("+diaSemana+","+horaInicio+"-"+horaFim+")", e);
        }
    }

    public void removeHorario(String diaSemana, String horaInicio, String horaFim) {
        try{
            Horario horario = findHorario(diaSemana, horaInicio, horaFim);
            if(horario != null){
                entityManager.remove(horario);
            } else {
                System.err.println("ERRO A ENCONTRAR HORARIO");
            }
        } catch (Exception e){
            System.err.println("ERRO A REMOVER HORARIO ("+diaSemana+","+horaInicio+"-"+horaFim+") ----> "+ e.toString());
        }
    }

    public void enrollHorarioInModalidade(String diaSemana, String horaInicio, String horaFim, String nome, String escalao){
        try{
            Horario horario = findHorario(diaSemana, horaInicio, horaFim);
            if (horario != null){
                Modalidade modalidade = entityManager.find(Modalidade.class, new ModalidadePK(nome, escalao));
                if (modalidade != null) {
                    modalidade.adicionarHorario(horario);
                    horario.addModalidade(modalidade);
                }else {
                    System.err.println("ERRO A ENCONTRAR MODALIDADE");
                }
            } else {
                System.err.println("ERRO A ENCONTRAR HORARIO");
            }
        }catch (Exception e) {
            throw new EJBException("ERRO A ADICIONAR HORARIO ("+diaSemana+","+horaInicio+"-"+horaFim+") NA MODALIDADE ("+nome+", "+escalao+")", e);
        }
    }

    public void unrollHorarioInModalidade(String diaSemana, String horaInicio, String horaFim, String nome, String escalao){
        try{
            Horario horario = findHorario(diaSemana, horaInicio, horaFim);
            if (horario != null){
                Modalidade modalidade = entityManager.find(Modalidade.class, new ModalidadePK(nome, escalao));
                if (modalidade != null) {
                    modalidade.removerHorario(horario);
                    horario.removeModalidade(modalidade);
                }else {
                    System.err.println("ERRO A ENCONTRAR MODALIDADE");
                }
            } else {
                System.err.println("ERRO A ENCONTRAR HORARIO");
            }
        }catch (Exception e) {
            throw new EJBException("ERRO A REMOVER HORARIO ("+diaSemana+","+horaInicio+"-"+horaFim+") DA MODALIDADE ("+nome+", "+escalao+")", e);
        }
    }

    public void unrollAllModalidadesFromHorario(String diaSemana, String horaInicio, String horaFim){
        try{
            Horario horario = findHorario(diaSemana, horaInicio, horaFim);
            if (horario != null) {
                if (!horario.getModalidades().isEmpty()) {
                    Modalidade modAux = new Modalidade();
                    for (Modalidade modalidade : horario.getModalidades()) {
                        modAux = entityManager.find(Modalidade.class, new ModalidadePK(modalidade.getNome(), modalidade.getEscalao()));
                        modAux.removerHorario(horario);
                    }
                    horario.removeAllModalidade();
                } else {
                    System.err.println("ATLETA NAO POSSUI MODALIDADES");
                }
            } else {
                System.err.println("ERRO A ENCONTRAR HORARIO");
            }
        }catch (Exception e) {
            throw new EJBException("ERRO A REMOVER TODAS AS MODALIDADES DO HORARIO ("+diaSemana+","+horaInicio+"-"+horaFim+")", e);
        }
    }
}
