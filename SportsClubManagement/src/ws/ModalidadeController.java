package ws;

import dtos.AtletaDTO;
import dtos.ModalidadeDTO;
import dtos.TreinadorDTO;
import ejbs.ModalidadeBean;
import entities.Atleta;
import entities.Modalidade;
import entities.Treinador;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/modalidades")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ModalidadeController {
    @EJB
    private ModalidadeBean modalidadeBean;

    ModalidadeDTO toDTO(Modalidade modalidade) {
        return new ModalidadeDTO(
                modalidade.getNome(),
                toTreinadorDTOs(modalidade.getTreinadores()),
                toAtletaDTOs(modalidade.getAtletas()),
                modalidade.getEscalao()
        );
    }

    List<ModalidadeDTO> toDTOs(List<Modalidade> modalidades) {
        return modalidades.stream().map(this::toDTO).collect(Collectors.toList());
    }

    ModalidadeDTO toNomeDTO(String modalidade) {
        return new ModalidadeDTO(
            modalidade
        );
    }

    List<ModalidadeDTO> toNomeDTOs(List<String> modalidades) {
        return modalidades.stream().map(this::toNomeDTO).collect(Collectors.toList());
    }

    ModalidadeDTO toEscalaoDTO(String modalidade) {
        return new ModalidadeDTO(
                modalidade,
                1
        );
    }

    List<ModalidadeDTO> toEscalaoDTOs(List<String> modalidades) {
        return modalidades.stream().map(this::toEscalaoDTO).collect(Collectors.toList());
    }

    AtletaDTO toAtletaDTO(Atleta atleta) {
        return new AtletaDTO(
                atleta.getUsername(),
                atleta.getPassword(),
                atleta.getName(),
                atleta.getEmail(),
                new HashSet<>()
        );
    }
    Set<AtletaDTO> toAtletaDTOs(Set<Atleta> atletas) {
        return atletas.stream().map(this::toAtletaDTO).collect(Collectors.toSet());
    }

    TreinadorDTO toTreinadorDTO(Treinador treinador) {
        return new TreinadorDTO(
                treinador.getUsername(),
                treinador.getName(),
                treinador.getPassword(),
                treinador.getEmail(),
                new HashSet<>()
        );
    }
    Set<TreinadorDTO> toTreinadorDTOs(Set<Treinador> treinadores) {
        return treinadores.stream().map(this::toTreinadorDTO).collect(Collectors.toSet());
    }

    @GET
    @Path("/")
    public List<ModalidadeDTO> all() {
        try {
            return toNomeDTOs(modalidadeBean.all());
        } catch (Exception e) {
            throw new EJBException("ERRO A OBTER AS MODALIDADES", e);
        }
    }

    @GET
    @Path("{nome}")
    public List<ModalidadeDTO> allModalidadeEscaloes(@PathParam("nome") String nome) {
        try {
            return toEscalaoDTOs(modalidadeBean.allModalidadeEscaloes(nome));
        } catch (Exception e) {
            throw new EJBException("ERRO A OBTER OS ESCALOES DA MODALIDADE: "+nome, e);
        }
    }

    @GET
    @Path("{nome}&&{escalao}")
    public Response getModalidadeDetails(@PathParam("nome") String nome, @PathParam("escalao") String escalao) {
        try {
            Modalidade modalidade = modalidadeBean.findModalidade(nome, escalao);
            if (modalidade != null) {
                return Response.status(Response.Status.OK).entity(toDTO(modalidade)).build();
            }
            System.err.println("ERRO A ENCONTRAR MODALIDADE");
        } catch (Exception e) {
            System.err.println("ERRO AO ADQUIRUIR OS DETALHES DA MODALIDADE --->" + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @POST
    @Path("/")
    public Response createNewModalidade (ModalidadeDTO modalidadeDTO){
        try{
            modalidadeBean.create(modalidadeDTO.getNome(),modalidadeDTO.getEscalao());
            Modalidade newModalidade = modalidadeBean.findModalidade(modalidadeDTO.getNome(), modalidadeDTO.getEscalao());
            if(newModalidade!=null)
                return Response.status(Response.Status.CREATED).entity(toDTO(newModalidade)).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e) {
            throw new EJBException("ERRO AO CRIAR MODALIDADE", e);
        }
    }

    @DELETE
    @Path("{nome}&&{escalao}")
    public Response removeModalidade(@PathParam("nome") String nome, @PathParam("escalao") String escalao) {
        try {
            Modalidade modalidade = modalidadeBean.findModalidade(nome, escalao);
            if (modalidade != null) {
                modalidadeBean.removeModalidade(nome, escalao);
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            throw new EJBException("ERRO A REMOVER MODALIDADE", e);
        }
    }
}
