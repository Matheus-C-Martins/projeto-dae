package ws;

import dtos.AtletaDTO;
import dtos.ModalidadeDTO;
import ejbs.AtletaBean;
import entities.Atleta;
import entities.Modalidade;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/atletas")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AtletaController {
    @EJB
    private AtletaBean atletaBean;

    /* ----- ATLETA SEM MODALIDADES ----- */        //EXISTE APENAS QUANDO O ATLETA E CRIADO
    AtletaDTO toDTOSemModalidades(Atleta atleta) {
        return new AtletaDTO(
                atleta.getUsername(),
                atleta.getPassword(),
                atleta.getName(),
                atleta.getEmail(),
                new HashSet<>()
        );
    }
    List<AtletaDTO> toDTOsSemModalidades(List<Atleta> atletas) {
        return atletas.stream().map(this::toDTOSemModalidades).collect(Collectors.toList());
    }
    /* ---------------------------------- */
    /* ------ MODALIDADES DO ATLETA ----- */
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
    /* ----- ATLETA COM MODALIDADES ----- */
    AtletaDTO toDTOComModalidades(Atleta atleta) {
        return new AtletaDTO(
                atleta.getUsername(),
                atleta.getPassword(),
                atleta.getName(),
                atleta.getEmail(),
                toModalidadeDTOs(atleta.getModalidades())
        );
    }
    List<AtletaDTO> toDTOsComModalidades(List<Atleta> atletas) {
        return atletas.stream().map(this::toDTOComModalidades).collect(Collectors.toList());
    }
    /* ---------------------------------- */

    @GET
    @Path("/")
    public List<AtletaDTO> all() {
        try {
            return toDTOsSemModalidades(atletaBean.all());
        } catch (Exception e) {
            throw new EJBException("ERRO A OBTER ATLETAS", e);
        }
    }

    @GET
    @Path("{username}")
    public Response getAtletaDetails(@PathParam("username") String username) {
        try {
            Atleta atleta = atletaBean.findAtleta(username);
            if (atleta != null) {
                return Response.status(Response.Status.OK).entity(toDTOSemModalidades(atleta)).build();
            }
            System.err.println("ERRO A ENCONTRAR ATLETA");
        } catch (Exception e) {
            System.err.println("ERRO AO ADQUIRUIR OS DETALHES DO ATLETA --->" + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("{username}/modalidades")
    public Response getAtletaModalidades(@PathParam("username") String username) {
        try {
            Atleta atleta = atletaBean.findAtleta(username);
            if (atleta != null) {
                GenericEntity<Set<ModalidadeDTO>> entity = new GenericEntity<>(toModalidadeDTOs(atleta.getModalidades())) {
                };
                return Response.status(Response.Status.OK).entity(entity).build();
            }
            System.err.println("ERRO A ENCONTRAR ATLETA");
        } catch (Exception e) {
            System.err.println("ERRO A OBTER AS MODALIDADES DO ATLETA ("+username+") --->" + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @POST
    @Path("/")
    public Response createNewAtleta (AtletaDTO atletaDTO){
        try{
            atletaBean.create(atletaDTO.getUsername(),
                    atletaDTO.getPassword(),
                    atletaDTO.getNome(),
                    atletaDTO.getEmail());
            Atleta newAtleta = atletaBean.findAtleta(atletaDTO.getUsername());
            if(newAtleta!=null) {
                return Response.status(Response.Status.CREATED).entity(toDTOSemModalidades(newAtleta)).build();
            }
            return Response.status(Response.Status.valueOf("Ocorreu um erro na criação do novo atleta!")).build();
        }catch (Exception e) {
            throw new EJBException("ERRO AO CRIAR ATLETA", e);
        }
    }

    @POST
    @Path("{username}/modalidades")
    public Response enrollModalidade(@PathParam("username") String username, ModalidadeDTO modalidadeDTO) {
        try {
            Atleta atleta = atletaBean.findAtleta(username);
            if (atleta != null) {
                atletaBean.enrollAtletaInModalidade(username, modalidadeDTO.getNome(), modalidadeDTO.getEscalao());
                return Response.status(Response.Status.OK).entity("MODALIDADE ADICIONADO COM SUCESSO").build();
            }
            System.err.println("ERRO A ENCONTRAR ATLETA");
        } catch (Exception e) {
            System.err.println("ERRO A ADICIONAR MODALIDADES AO ATLETA --->" + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @PUT
    @Path("{username}")
    public Response updateAtleta(@PathParam("username") String username, AtletaDTO atletaDTO) {
        try {
            Atleta atleta = atletaBean.findAtleta(username);
            if (atleta != null) {
                atletaBean.updateAtleta(username, atletaDTO.getPassword(), atletaDTO.getNome(), atletaDTO.getEmail());
                return Response.status(Response.Status.OK).entity(toDTOSemModalidades(atleta)).build();
            }
            return Response.status(Response.Status.valueOf("Atleta com o username: '"+ username +"' não existe!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO A ATUALIZAR ATLETA", e);
        }
    }

    @DELETE
    @Path("{username}")
    public Response removeAtleta(@PathParam("username") String username) {
        try {
            Atleta atleta = atletaBean.findAtleta(username);
            if (atleta != null) {
                if(!atleta.getModalidades().isEmpty()){
                    atletaBean.unrollAllModalidadesFromAtleta(username);
                }
                atletaBean.removeAtleta(username);
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.valueOf("Atleta com o username: '"+ username +"' não existe!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO A REMOVER ATLETA", e);
        }
    }

    @DELETE
    @Path("{username}/modalidades/{nome}&&{escalao}")
    public Response unrollModalidade(@PathParam("username") String username, @PathParam("nome") String nome, @PathParam("escalao") String escalao) {
        try {
            Atleta atleta = atletaBean.findAtleta(username);
            if (atleta != null) {
                atletaBean.unrollAtletaInModalidade(username, nome, escalao);
                return Response.status(Response.Status.OK).entity("MODALIDADE REMOVIDA COM SUCESSO").build();
            }
            System.err.println("ERRO A ENCONTRAR ATLETA");
        } catch (Exception e) {
            System.err.println("ERRO A REMOVER MODALIDADES AO ATLETA --->" + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
