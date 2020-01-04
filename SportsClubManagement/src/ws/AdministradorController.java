package ws;

import dtos.AdministradorDTO;
import ejbs.AdministradorBean;
import entities.Administrador;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.stream.Collectors;

@Path("/administradores")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AdministradorController {
    @Context
    private SecurityContext securityContext;

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
    List<AdministradorDTO> toDTOs(List<Administrador> administradors) { return administradors.stream().map(this::toDTO).collect(Collectors.toList()); }

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
            if(newAdmin!=null) {
                return Response.status(Response.Status.CREATED).entity(toDTO(newAdmin)).build();
            }
            return Response.status(Response.Status.valueOf("Ocorreu um erro na criação do novo administrador!")).build();
        }catch (Exception e) {
            throw new EJBException("ERRO AO CRIAR ADMINISTRADOR", e);
        }
    }

    @PUT
    @Path("{username}")
    public Response updateAdmin(@PathParam("username") String username, AdministradorDTO administradorDTO) {
        try {
            Administrador admin = administradorBean.findAdmin(username);
            if (admin != null) {
                administradorBean.updateAdmin(username, administradorDTO.getPassword(), administradorDTO.getNome(), administradorDTO.getEmail());
                return Response.status(Response.Status.OK).entity(toDTO(admin)).build();
            }
            return Response.status(Response.Status.valueOf("Administrador com o username: '"+ username +"' não existe!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO A ATUALIZAR ADMINISTRADOR", e);
        }
    }

    @DELETE
    @Path("{username}")
    public Response removeAdmin(@PathParam("username") String username) {
        try {
            Administrador admin = administradorBean.findAdmin(username);
            if (admin != null) {
                administradorBean.removeAdmin(username);
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.valueOf("Administrador com o username: '"+ username +"' não existe!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO A REMOVER AMDMINISTRADOR", e);
        }
    }
}
