package ws;

import dtos.ModalidadeDTO;
import ejbs.ModalidadeBean;
import entities.Modalidade;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/modalidades") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class ModalidadeController {
    @EJB
    private ModalidadeBean modalidadeBean;

    ModalidadeDTO toDTO(Modalidade modalidade) {
        return new ModalidadeDTO(
                modalidade.getNome()
        );
    }

    List<ModalidadeDTO> toDTOs(List<Modalidade> modalidades) {
        return modalidades.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ModalidadeDTO> all() {
        try {
            return toDTOs(modalidadeBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_MODALIDADES", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewModalidade (ModalidadeDTO modalidadeDTO){
        try{
            modalidadeBean.create(modalidadeDTO.getNome(),modalidadeDTO.getTreinadores(),
                    modalidadeDTO.getEscaloes(),
                    modalidadeDTO.getAtletas());
            Modalidade newModalidade = modalidadeBean.findModalidade(modalidadeDTO.getNome());
            if(newModalidade!=null)
                return Response.status(Response.Status.CREATED)
                        .entity(toDTO(newModalidade))
                        .build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e) {
            throw new EJBException("ERRO_AO_CRIAR_MODALIDADE", e);
        }
    }

    @PUT
    @Path("{nome}")
    public Response updateModalidade(@PathParam("nome") String nome, ModalidadeDTO modalidadeDTO) {
        try {
            Modalidade modalidade = modalidadeBean.findModalidade(nome);
            if (modalidade != null) {
                modalidadeBean.updateModalidade(nome, modalidadeDTO.getTreinadores(), modalidadeDTO.getEscaloes(), modalidadeDTO.getAtletas());
                return Response.status(Response.Status.OK)
                        .entity(toDTO(modalidade))
                        .build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            throw new EJBException("ERRO_A_ATUALIZAR_MODALIDADE", e);
        }
    }

    @DELETE
    @Path("{nome}")
    public Response removeAdmin(@PathParam("nome") String nome) {
        try {
            Modalidade modalidade = modalidadeBean.findModalidade(nome);
            if (modalidade != null) {
                modalidadeBean.removeModalidade(nome);
                return Response.status(Response.Status.OK)
                        .build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            throw new EJBException("ERRO_A_REMOVER_MODALIDADE", e);
        }
    }
}
