package ws;

import dtos.AdministradorDTO;
import ejbs.AdministradorBean;
import entities.Administrador;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
}
