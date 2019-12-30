package ws;

import dtos.ProdutoDTO;
import ejbs.ProdutoBean;
import entities.Produto;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/produtos")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProdutoController {
    @EJB
    private ProdutoBean produtoBean;

    ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getTipo(),
                produto.getDescricao(),
                produto.getValorBase()
        );
    }
    List<ProdutoDTO> toDTOs(List<Produto> produtos) {
        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<ProdutoDTO> all() {
        try {
            return toDTOs(produtoBean.all());
        } catch (Exception e) {
            throw new EJBException("ERRO AO OBTER PRODUTOS");
        }
    }

    @GET
    @Path("{tipo}")
    public Response getProdutoDetails(@PathParam("tipo") String tipo) {
        Produto produto = produtoBean.findProduto(tipo);
        return Response.status(Response.Status.OK).entity(toDTO(produto)).build();
    }

    @POST
    @Path("/")
    public Response createNewProduto(ProdutoDTO produtoDTO) {
        try {
            produtoBean.create(
                    produtoDTO.getTipo(),
                    produtoDTO.getDescrição(),
                    produtoDTO.getValorBase());
            Produto newProd = produtoBean.findProduto(produtoDTO.getTipo());
            if (newProd != null) {
                return Response.status(Response.Status.CREATED).entity(toDTO(newProd)).build();
            }
            return Response.status(Response.Status.valueOf("Ocorreu um erro na criação do novo produto!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO AO CRIAR PRODUTO", e);
        }
    }

    @PUT
    @Path("{tipo}")
    public Response updateProduto(@PathParam("tipo") String tipo, ProdutoDTO produtoDTO) {
        try {
            Produto produto = produtoBean.findProduto(tipo);
            if (produto != null) {
                produtoBean.updateProduto(tipo, produtoDTO.getDescrição(), produtoDTO.getValorBase());
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.valueOf("Produto com o username: '"+ tipo +"' não existe!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO AO ACTUALIZAR PRODUTO COM O TIPO " + tipo + "!", e);
        }
    }

    @DELETE
    @Path("{tipo}")
    public Response deleteSocio(@PathParam("tipo") String tipo) {
        try {
            Produto produto = produtoBean.findProduto(tipo);
            if (produto != null) {
                produtoBean.removeProduto(tipo);
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.valueOf("Produto com o username: '"+ tipo +"' não existe!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO AO ELIMINAR O PRODUTO COM O USERNAME " + tipo + "!");
        }
    }
}
