package br.com.fiap.resource;

import br.com.fiap.dao.LocalidadeDao;
import br.com.fiap.dto.LocalidadeDto;
import br.com.fiap.exceptions.ErroDeBancoDeDadosException;
import br.com.fiap.exceptions.RecursoNaoEncontradoException;
import br.com.fiap.exceptions.ValidacaoException;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.service.LocalidadeService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/localidades")
public class LocalidadeResource {
    private LocalidadeService localidadeService;

    public LocalidadeResource() throws SQLException, ClassNotFoundException {
        this.localidadeService = new LocalidadeService(new LocalidadeDao(ConnectionFactory.getConnection()));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLocalidades() throws SQLException {
        List<LocalidadeDto> localidades = localidadeService.getAllLocalidades();
        return Response.ok(localidades).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLocalidadeById(@PathParam("id") int id) {
        try {
            LocalidadeDto localidade = localidadeService.getLocalidadeById(id);
            if (localidade == null) {
                throw new RecursoNaoEncontradoException("Localidade com ID " + id + " não encontrada.");
            }
            return Response.ok(localidade).build();
        } catch (RecursoNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            throw new ErroDeBancoDeDadosException("Erro ao buscar a localidade: " + e.getMessage());
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addLocalidade(LocalidadeDto localidadeDto) {
        try {
            localidadeService.addLocalidade(localidadeDto);
            return Response.status(Response.Status.CREATED).build();
        } catch (ValidacaoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            throw new ErroDeBancoDeDadosException("Erro ao adicionar localidade: " + e.getMessage());
        }
    }


    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateLocalidade(@PathParam("id") int id, LocalidadeDto localidadeDto) throws SQLException {
        localidadeService.updateLocalidade(id, localidadeDto);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteLocalidade(@PathParam("id") int id) throws SQLException {
        localidadeService.deleteLocalidade(id);
        return Response.noContent().build();
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
