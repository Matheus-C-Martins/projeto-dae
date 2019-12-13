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
            modalidadeBean.create(modalidadeDTO.getNome());
            Modalidade newModalidade = modalidadeBean.findModalidade(modalidadeDTO.getNome(),
                    modalidadeDTO.getTreinadores(),
                    modalidadeDTO.getEscaloes(),
                    modalidadeDTO.getAtletas());
            if(newModalidade!=null)
                return Response.status(Response.Status.CREATED)
                        .entity(toDTO(newModalidade))
                        .build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e) {
            throw new EJBException("ERRO_AO_CRIAR_MODALIDADE", e);
        }
    }
}
