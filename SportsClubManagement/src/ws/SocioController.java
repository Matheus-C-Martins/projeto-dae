package ws;

import dtos.EmailDTO;
import dtos.SocioDTO;
import ejbs.AtletaBean;
import ejbs.EmailBean;
import ejbs.SocioBean;
import ejbs.TreinadorBean;
import entities.Atleta;
import entities.Socio;
import entities.Treinador;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.mail.MessagingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/socios")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SocioController {
    @EJB
    private SocioBean socioBean;
    @EJB
    private TreinadorBean treinadorBean;
    @EJB
    private AtletaBean atletaBean;
    @EJB
    private EmailBean emailBean;

    SocioDTO toDTO(Socio socio) {
        return new SocioDTO(
            socio.getUsername(),
            socio.getPassword(),
            socio.getName(),
            socio.getEmail()
        );
    }
    List<SocioDTO> toDTOs(List<Socio> socios) {
        return socios.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<SocioDTO> all() {
        try {
            return toDTOs(socioBean.all());
        } catch (Exception e) {
            throw new EJBException("ERRO AO OBTER SOCIOS");
        }
    }

    @GET
    @Path("{username}")
    public Response getSocioDetails(@PathParam("username") String username) {
        Socio socio = socioBean.findSocio(username);
        return Response.status(Response.Status.OK).entity(toDTO(socio)).build();
    }

    @POST
    @Path("/")
    public Response createNewSocio(SocioDTO socioDTO) {
        try {
            socioBean.create(
                    socioDTO.getUsername(),
                    socioDTO.getPassword(),
                    socioDTO.getNome(),
                    socioDTO.getEmail());
            Socio newSocio = socioBean.findSocio(socioDTO.getUsername());
            if (newSocio != null) {
                return Response.status(Response.Status.CREATED).entity(toDTO(newSocio)).build();
            }
            return Response.status(Response.Status.valueOf("Ocorreu um erro na criação do novo sócio!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO AO CRIAR SOCIO", e);
        }
    }

    @POST
    @Path("/{username}/email/send")
    public Response sendEmail(@PathParam("username") String username, EmailDTO email) throws MessagingException {
        try {
            Socio socio = socioBean.findSocio(username);
            if (socio != null) {
                emailBean.send(socio.getEmail(), email.getAssunto(), email.getMensagem());
                return Response.status(Response.Status.OK).entity("E-mail enviado").build();
            }
            System.err.println("ERRO A ENCONTRAR SOCIO");
        } catch (Exception e) {
            System.err.println("ERRO A ENVIAR EMAIL AO SOCIO --->" + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @PUT
    @Path("{username}")
    public Response updateSocio(@PathParam("username") String username, SocioDTO socioDTO) {
        try {
            Socio socio = socioBean.findSocio(username);
            if (socio != null) {
                socioBean.updateSocio(username, socioDTO.getNome(), socioDTO.getPassword(), socioDTO.getEmail());
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.valueOf("Sócio com o username: '"+ username +"' não existe!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO AO ACTUALIZAR SOCIO COM O USERNAME " + username + "!", e);
        }
    }

    @DELETE
    @Path("{username}")
    public Response deleteSocio(@PathParam("username") String username) {
        try {
            Socio socio = socioBean.findSocio(username);
            if (socio != null) {
                socioBean.removeSocio(username);
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.valueOf("Sócio com o username: '"+ username +"' não existe!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO AO ELIMINAR O SOCIO COM O USERNAME " + username + "!");
        }
    }
}
