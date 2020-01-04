package ws;

import dtos.ModalidadeDTO;
import dtos.TreinadorDTO;
import ejbs.TreinadorBean;
import entities.Atleta;
import entities.Modalidade;
import entities.Treinador;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/treinadores")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class TreinadorController {
    @EJB
    private TreinadorBean treinadorBean;

    /* ----- TREINADOR SEM MODALIDADE ----- */
    TreinadorDTO toDTOSemModalidades(Treinador treinador) {
        return new TreinadorDTO(
                treinador.getUsername(),
                treinador.getName(),
                treinador.getPassword(),
                treinador.getEmail(),
                new HashSet<>()
        );
    }
    List<TreinadorDTO> toDTOsSemModalidades(List<Treinador> treinadores) {
        return treinadores.stream().map(this::toDTOSemModalidades).collect(Collectors.toList());
    }
    /* ------------------------------------ */
    /* ------------------------------------ */
    /* ------ MODALIDADES DO TREINADOR ------- */
    ModalidadeDTO toModalidadeDTO(Modalidade modalidade) {
        return new ModalidadeDTO(
                modalidade.getNome(),
                modalidade.getEscalao()
        );
    }

    Set<ModalidadeDTO> toModalidadeDTOs(Set<Modalidade> modalidades) {
        return modalidades.stream().map(this::toModalidadeDTO).collect(Collectors.toSet());
    }
    /* ------------------------------------ */
    /* ----- TREINADOR COM MODALIDADE ----- */
    TreinadorDTO toDTOComModalidades(Treinador treinador) {
        return new TreinadorDTO(
                treinador.getUsername(),
                treinador.getName(),
                treinador.getPassword(),
                treinador.getEmail(),
                toModalidadeDTOs(treinador.getModalidades())
        );
    }
    List<TreinadorDTO> toDTOsComModalidades(List<Treinador> treinadores) {
        return treinadores.stream().map(this::toDTOComModalidades).collect(Collectors.toList());
    }
    /* ------------------------------------ */

    @GET
    @Path("/")
    public List<TreinadorDTO> all() {
        try {
            return toDTOsSemModalidades(treinadorBean.all());
        } catch (Exception e) {
            throw new EJBException("ERRO A OBTER TREINADORES", e);
        }
    }

    @GET
    @Path("{username}")
    public Response getTreinadorDetails(@PathParam("username") String username) {
        try {
            Treinador treinador = treinadorBean.findTreinador(username);
            if (treinador != null) {
                return Response.status(Response.Status.OK).entity(toDTOSemModalidades(treinador)).build();
            }
            System.err.println("ERRO A ENCONTRAR TREINADOR");
        } catch (Exception e) {
            System.err.println("ERRO AO ADQUIRUIR OS DETALHES DO TREINADOR --->" + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("{username}/modalidades")
    public Response getTreinadorModalidades(@PathParam("username") String username) {
        try {
            Treinador treinador = treinadorBean.findTreinador(username);
            if (treinador != null) {
                GenericEntity<Set<ModalidadeDTO>> entity = new GenericEntity<>(toModalidadeDTOs(treinador.getModalidades())) {
                };
                return Response.status(Response.Status.OK).entity(entity).build();
            }
            System.err.println("ERRO A ENCONTRAR TREINADOR");
        } catch (Exception e) {
            System.err.println("ERRO A OBTER AS MODALIDADES DO TREINADOR ("+username+") --->" + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @POST
    @Path("/")
    public Response createNewTreinador (TreinadorDTO treinadorDTO){
        try{
            treinadorBean.create(treinadorDTO.getUsername(),
                    treinadorDTO.getPassword(),
                    treinadorDTO.getNome(),
                    treinadorDTO.getEmail());
            Treinador newTreinador = treinadorBean.findTreinador(treinadorDTO.getUsername());
            if(newTreinador != null) {
                return Response.status(Response.Status.CREATED).entity(toDTOSemModalidades(newTreinador)).build();
            }
            return Response.status(Response.Status.valueOf("Ocorreu um erro na criação do novo treinador!")).build();
        }catch (Exception e) {
            throw new EJBException("ERRO AO CRIAR TREINADOR", e);
        }
    }

    @PUT
    @Path("{username}")
    public Response updateTreinador(@PathParam("username") String username, TreinadorDTO treinadorDTO) {
        try {
            Treinador treinador = treinadorBean.findTreinador(username);
            if (treinador != null) {
                treinadorBean.updateTreinador(username, treinadorDTO.getPassword(), treinadorDTO.getNome(), treinadorDTO.getEmail());
                return Response.status(Response.Status.OK).entity(toDTOSemModalidades(treinador)).build();
            }
            return Response.status(Response.Status.valueOf("Treinador com o username: '"+ username +"' não existe!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO A ATUALIZAR TREINADOR", e);
        }
    }
    @POST
    @Path("{username}/modalidades")
    public Response enrollModalidade(@PathParam("username") String username, ModalidadeDTO modalidadeDTO) {
        try {
            Treinador treinador = treinadorBean.findTreinador(username);
            if (treinador != null) {
                treinadorBean.enrollTreinadorInModalidade(username, modalidadeDTO.getNome(), modalidadeDTO.getEscalao());
                return Response.status(Response.Status.OK).entity("MODALIDADE ADICIONADA COM SUCESSO").build();
            }
            System.err.println("ERRO A ENCONTRAR TREINADOR");
        } catch (Exception e) {
            System.err.println("ERRO A ADICIONAR MODALIDADE AO TREINADOR --->" + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @DELETE
    @Path("{username}")
    public Response removeTreinador(@PathParam("username") String username) {
        try {
            Treinador treinador = treinadorBean.findTreinador(username);
            if (treinador != null) {
                if(!treinador.getModalidades().isEmpty()){
                    treinadorBean.unrollAllModalidadesFromTreinador(username);
                }
                treinadorBean.removeTreinador(username);
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.valueOf("Treinador com o username: '"+ username +"' não existe!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO A REMOVER TREINADOR", e);
        }
    }

    @DELETE
    @Path("{username}/modalidades/{nome}&&{escalao}")
    public Response unrollModalidade(@PathParam("username") String username, @PathParam("nome") String nome, @PathParam("escalao") String escalao) {
        try {
            Treinador treinador = treinadorBean.findTreinador(username);
            if (treinador != null) {
                treinadorBean.unrollTreinadorInModalidade(username, nome, escalao);
                return Response.status(Response.Status.OK).entity("MODALIDADE REMOVIDA COM SUCESSO").build();
            }
            System.err.println("ERRO A ENCONTRAR TREINADOR");
        } catch (Exception e) {
            System.err.println("ERRO A REMOVER MODALIDADES DO TREINADOR --->" + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
