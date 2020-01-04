package ws;

import dtos.PagamentoDTO;
import ejbs.PagamentoBean;
import entities.Pagamento;
import entities.Produto;
import entities.User;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/pagamentos")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PagamentoController {
    @EJB
    private PagamentoBean pagamentoBean;

    PagamentoDTO toDTO(Pagamento pagamento) {
        return new PagamentoDTO(
                pagamento.getUsername(),
                pagamento.getTipoProduto(),
                pagamento.getQuantidade(),
                pagamento.getPreco(),
                pagamento.getEstado()
        );
    }

    List<PagamentoDTO> toDTOs(List<Pagamento> pagamentos) {
        return pagamentos.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<PagamentoDTO> all() {
        try {
            return toDTOs(pagamentoBean.all());
        } catch (Exception e) {
            throw new EJBException("ERRO AO OBTER PRODUTOS");
        }
    }

    @GET
    @Path("{username}&&{tipoProduto}")
    public Response getPagamentoDetails(@PathParam("username") String username, @PathParam("tipoProduto") String tipoProduto) {
        Pagamento pagamento = pagamentoBean.findPagamento(username, tipoProduto);
        return Response.status(Response.Status.OK).entity(toDTO(pagamento)).build();
    }

    @POST
    @Path("/")
    public Response createNewPagamento(PagamentoDTO pagamentoDTO) {
        try {
            pagamentoBean.create(
                    pagamentoDTO.getUsername(),
                    pagamentoDTO.getTipoProduto(),
                    pagamentoDTO.getQuantidade(),
                    pagamentoDTO.getPreco(),
                    pagamentoDTO.getEstado());
            Pagamento newPag = pagamentoBean.findPagamento(pagamentoDTO.getUsername(),pagamentoDTO.getTipoProduto());
            if (newPag != null) {
                return Response.status(Response.Status.CREATED).entity(toDTO(newPag)).build();
            }
            return Response.status(Response.Status.valueOf("Ocorreu um erro na criação do novo pagamento!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO AO CRIAR PAGAMENTO", e);
        }
    }

    @PUT
    @Path("{username}&&{tipoProduto}")
    public Response updatePagamento(@PathParam("username") String username, @PathParam("tipoProduto") String tipoProduto, PagamentoDTO pagamentoDTO) {
        try {
            Pagamento pagamento = pagamentoBean.findPagamento(username, tipoProduto);
            if (pagamento != null) {
                pagamentoBean.updatePagamento(username, tipoProduto, pagamentoDTO.getQuantidade(),pagamentoDTO.getPreco(),pagamentoDTO.getEstado());
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.valueOf("Pagamento não existe!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO AO ACTUALIZAR PAGAMENTO !", e);
        }
    }

    @DELETE
    @Path("{username}&&{tipoProduto}")
    public Response deleteSocio(@PathParam("username") String username, @PathParam("tipoProduto") String tipoProduto, PagamentoDTO pagamentoDTO) {
        try {
            Pagamento pagamento = pagamentoBean.findPagamento(username, tipoProduto);
            if (pagamento != null) {
                pagamentoBean.removePagamento(username, tipoProduto);
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.valueOf("Pagamento não existe!")).build();
        } catch (Exception e) {
            throw new EJBException("ERRO AO ELIMINAR O PAGAMENTO !");
        }
    }
}
