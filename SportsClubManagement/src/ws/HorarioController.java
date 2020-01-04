package ws;

import dtos.AtletaDTO;
import dtos.HorarioDTO;
import dtos.ModalidadeDTO;
import ejbs.HorarioBean;
import entities.Atleta;
import entities.Horario;
import entities.Modalidade;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/horarios")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class HorarioController {
    @EJB
    private HorarioBean horarioBean;

    /* ----- HORARIO COM MODALIDADES ----- */
    HorarioDTO toDTOSemModalidades(Horario horario) {
        return new HorarioDTO(
                horario.getDiaSemana(),
                horario.getHoraInicio(),
                horario.getHoraFim(),
                new HashSet<>()
        );
    }
    List<HorarioDTO> toDTOsSemModalidades(List<Horario> horarios) {
        return horarios.stream().map(this::toDTOSemModalidades).collect(Collectors.toList());
    }
    /* ---------------------------------- */
    /* ----- HORARIO COM MODALIDADES ----- */
    HorarioDTO toDTOComModalidades(Horario horario) {
        return new HorarioDTO(
                horario.getDiaSemana(),
                horario.getHoraInicio(),
                horario.getHoraFim(),
                toModalidadeDTOs(horario.getModalidades())
        );
    }
    List<HorarioDTO> toDTOsComModalidades(List<Horario> horarios) {
        return horarios.stream().map(this::toDTOComModalidades).collect(Collectors.toList());
    }
    /* ---------------------------------- */
    /* ------ MODALIDADES DO HORARIO ----- */
    ModalidadeDTO toModalidadeDTO(Modalidade modalidade) {
        return new ModalidadeDTO(
                modalidade.getNome(),
                modalidade.getEscalao()
        );
    }

    Set<ModalidadeDTO> toModalidadeDTOs(Set<Modalidade> modalidades) {
        return modalidades.stream().map(this::toModalidadeDTO).collect(Collectors.toSet());
    }
    /* ---------------------------------- */

    @GET
    @Path("/")
    public List<HorarioDTO> all() {
        try {
            return toDTOsSemModalidades(horarioBean.all());
        } catch (Exception e) {
            throw new EJBException("ERRO A OBTER HORARIOS", e);
        }
    }

    @POST
    @Path("{diaSemana}&&{horaInicio}&&{horaFim}/modalidades")
    public Response enrollModalidade(@PathParam("diaSemana") String diaSemana, @PathParam("horaInicio") String horaInicio, @PathParam("horaFim") String horaFim, ModalidadeDTO modalidadeDTO) {
        try {
            Horario horario = horarioBean.findHorario(diaSemana, horaInicio, horaFim);
            if (horario != null) {
                horarioBean.enrollHorarioInModalidade(diaSemana, horaInicio, horaFim, modalidadeDTO.getNome(), modalidadeDTO.getEscalao());
                return Response.status(Response.Status.OK).entity("MODALIDADE ADICIONADA COM SUCESSO").build();
            } else {
                horarioBean.create(diaSemana, horaInicio, horaFim);
                horario = horarioBean.findHorario(diaSemana, horaInicio, horaFim);
                if (horario != null) {
                    horarioBean.enrollHorarioInModalidade(diaSemana, horaInicio, horaFim, modalidadeDTO.getNome(), modalidadeDTO.getEscalao());
                    return Response.status(Response.Status.OK).entity("MODALIDADE ADICIONADA COM SUCESSO").build();
                } else {
                    System.err.println("ERRO A ENCONTRAR HORARIO");
                }
            }
        } catch (Exception e) {
            System.err.println("ERRO A ADICIONAR MODALIDADES AO HORARIO --->" + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @DELETE
    @Path("{diaSemana}&&{horaInicio}&&{horaFim}")
    public Response removeAtleta(@PathParam("diaSemana") String diaSemana, @PathParam("horaInicio") String horaInicio, @PathParam("horaFim") String horaFim) {
        try {
            Horario horario = horarioBean.findHorario(diaSemana, horaInicio, horaFim);
            if (horario != null) {
                if(!horario.getModalidades().isEmpty()){
                    horarioBean.unrollAllModalidadesFromHorario(diaSemana, horaInicio, horaFim);
                }
                horarioBean.removeHorario(diaSemana, horaInicio, horaFim);
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.valueOf("Horário no dia '"+diaSemana+"' e entre as horas '"+horaInicio+"-"+horaFim+"' não existe!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO A REMOVER HORARIO", e);
        }
    }

    @DELETE
    @Path("{diaSemana}&&{horaInicio}&&{horaFim}/modalidades/{nome}&&{escalao}")
    public Response unrollModalidade(@PathParam("diaSemana") String diaSemana, @PathParam("horaInicio") String horaInicio, @PathParam("horaFim") String horaFim, @PathParam("nome") String nome, @PathParam("escalao") String escalao) {
        try {
            Horario horario = horarioBean.findHorario(diaSemana, horaInicio, horaFim);
            if (horario != null) {
                horarioBean.unrollHorarioInModalidade(diaSemana, horaInicio, horaFim, nome, escalao);
                return Response.status(Response.Status.OK).entity("MODALIDADE REMOVIDA COM SUCESSO").build();
            }
            System.err.println("ERRO A ENCONTRAR HORARIO");
        } catch (Exception e) {
            System.err.println("ERRO A REMOVER MODALIDADES AO HORARIO --->" + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
