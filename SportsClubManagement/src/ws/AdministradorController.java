package ws;

import dtos.AdministradorDTO;
import ejbs.AdministradorBean;
import entities.Administrador;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/administradores") // relative url web path of this controller
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class AdministradorController {
    @EJB
    private AdministradorBean administradorBean;

    AdministradorDTO toDTO(Administrador administrador) {
        return new AdministradorDTO(
                administrador.getUsername(),
                administrador.getPassword(),
                administrador.getName(),
                administrador.getEmail()
        );
    }

    List<AdministradorDTO> toDTOs(List<Administrador> administradors) {
        return administradors.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<AdministradorDTO> all() {
        try {
            return toDTOs(administradorBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_ADMINISTRADORES", e);
        }
    }

    @POST
    @Path("/")
    public Response createNewAdmin (AdministradorDTO administradorDTO){
        try{
            administradorBean.create(administradorDTO.getUsername(),
                    administradorDTO.getPassword(),
                    administradorDTO.getNome(),
                    administradorDTO.getEmail());
            Administrador newAdmin = administradorBean.findAdmin(administradorDTO.getUsername());
            if(newAdmin!=null)
                return Response.status(Response.Status.CREATED)
                        .entity(toDTO(newAdmin))
                        .build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }catch (Exception e) {
            throw new EJBException("ERRO_AO_CRIAR_ADMINISTRADOR", e);
        }
    }
}
