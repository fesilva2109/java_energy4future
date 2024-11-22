package br.com.fiap.resource;

import br.com.fiap.dao.InvestimentosDao;
import br.com.fiap.dto.InvestimentosDto;
import br.com.fiap.exceptions.ErroDeBancoDeDadosException;
import br.com.fiap.exceptions.RecursoNaoEncontradoException;
import br.com.fiap.exceptions.ValidacaoException;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.service.InvestimentosService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/investimentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InvestimentosResource {
    private InvestimentosService investimentosService;

    public InvestimentosResource() throws SQLException, ClassNotFoundException {
        this.investimentosService = new InvestimentosService(new InvestimentosDao(ConnectionFactory.getConnection()));
    }

    @POST
    public Response createInvestment(InvestimentosDto investimentosDto) {
        try {
            investimentosService.createInvestment(investimentosDto);
            return Response.status(Response.Status.CREATED).build();
        } catch (ValidacaoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            throw new ErroDeBancoDeDadosException("Erro ao criar o investimento: " + e.getMessage());
        }
    }

    @GET
    @Path("/{id}")
    public Response getInvestmentById(@PathParam("id") int id) {
        try {
            InvestimentosDto dto = investimentosService.getInvestmentById(id);
            if (dto == null) {
                throw new RecursoNaoEncontradoException("Investimento com ID " + id + " não encontrado.");
            }
            return Response.ok(dto).build();
        } catch (RecursoNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            throw new ErroDeBancoDeDadosException("Erro ao buscar o investimento: " + e.getMessage());
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllInvestments() throws SQLException {
        List<InvestimentosDto> investimentos = investimentosService.getAllInvestments();
        return Response.ok(investimentos).build();
    }

    @PUT
    public Response updateInvestment(InvestimentosDto investimentosDto) {
        try {
            investimentosService.updateInvestment(investimentosDto);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteInvestment(@PathParam("id") int id) {
        try {
            investimentosService.deleteInvestment(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @OPTIONS
    @Path("{path: .*}")
    public Response handlePreFlight(@HeaderParam("Access-Control-Request-Headers") String requestHeaders) {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", requestHeaders != null ? requestHeaders : "Content-Type, Authorization")
                .build();
    }
}
