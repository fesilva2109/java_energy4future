package br.com.fiap.resource;

import br.com.fiap.dao.UsuarioDao;
import br.com.fiap.dto.UsuarioDto;
import br.com.fiap.exceptions.ErroDeBancoDeDadosException;
import br.com.fiap.exceptions.OperacaoNaoPermitidaException;
import br.com.fiap.exceptions.ValidacaoException;
import br.com.fiap.service.SessaoUsuarioService;
import br.com.fiap.service.UsuarioService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuarios")
public class UsuarioResource {

    private final UsuarioService usuarioService;

    public UsuarioResource() {
        SessaoUsuarioService sessaoUsuarioService = SessaoUsuarioService.getInstance();
        this.usuarioService = new UsuarioService(new UsuarioDao(), sessaoUsuarioService);
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UsuarioDto usuario) {
        try {
            return usuarioService.login(usuario.getEmail(), usuario.getSenha())
                    .map(sessao -> Response.ok(sessao).build())
                    .orElseThrow(() -> new OperacaoNaoPermitidaException("Usuário ou senha inválidos"));
        } catch (OperacaoNaoPermitidaException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        } catch (Exception e) {
            throw new ErroDeBancoDeDadosException("Erro ao processar o login: " + e.getMessage());
        }
    }
    @POST
    @Path("/logout/{usuarioId}")
    public Response logout(@PathParam("usuarioId") int usuarioId) {
        usuarioService.logout(usuarioId);
        return Response.ok("Logout realizado com sucesso").build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuario(@PathParam("id") int id) {
        if (!usuarioService.isUsuarioLogado(id)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Usuário não está logado").build();
        }
        return Response.ok("Usuário está logado").build();
    }

    @GET
    @Path("/tempo/{usuarioId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTempoDesdeUltimoLogin(@PathParam("usuarioId") int usuarioId) {
        long minutos = usuarioService.getTempoDesdeUltimoLogin(usuarioId);
        if (minutos == -1) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sessão não encontrada").build();
        }
        return Response.ok("Tempo desde o último login: " + minutos + " minutos").build();
    }

    // Método para criar um novo usuário
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarUsuario(UsuarioDto usuario) {
        try {
            usuario.validarUsuario();
            usuarioService.criarUsuario(usuario);
            return Response.status(Response.Status.CREATED).entity(usuario).build();
        } catch (ValidacaoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            throw new ErroDeBancoDeDadosException("Erro ao criar o usuário: " + e.getMessage());
        }
    }

    // Endpoint para deletar um usuário
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletarUsuario(@PathParam("id") int id) {
        try {
            usuarioService.deletarUsuario(id);
            return Response.status(Response.Status.NO_CONTENT).entity("Usuário deletado com sucesso").build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Erro ao deletar usuário: " + e.getMessage()).build();
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
