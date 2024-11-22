package br.com.fiap.service;

import br.com.fiap.dao.UsuarioDao;
import br.com.fiap.dto.SessaoUsuarioDto;
import br.com.fiap.dto.UsuarioDto;

import java.sql.SQLException;
import java.util.Optional;

public class UsuarioService {

    private final UsuarioDao usuarioDao;
    private final SessaoUsuarioService sessaoUsuarioService;

    public UsuarioService(UsuarioDao usuarioDao, SessaoUsuarioService sessaoUsuarioService) {
        this.usuarioDao = usuarioDao;
        this.sessaoUsuarioService = sessaoUsuarioService;
    }

    // Método para fazer login do usuário
    public Optional<SessaoUsuarioDto> login(String email, String senha) throws SQLException, ClassNotFoundException {
        UsuarioDto usuario = usuarioDao.buscarPorEmailSenha(email, senha);
        if (usuario != null) {
            sessaoUsuarioService.criarSessao(usuario.getId());
            return Optional.of(new SessaoUsuarioDto(0, usuario.getId(), java.time.LocalDateTime.now()));
        }
        return Optional.empty();
    }

    // Método para fazer logout do usuário
    public void logout(int usuarioId) {
        sessaoUsuarioService.encerrarSessao(usuarioId);
    }

    // Método para verificar se o usuário está logado
    public boolean isUsuarioLogado(int usuarioId) {
        return sessaoUsuarioService.isSessaoAtiva(usuarioId);
    }

    // Método para pegar o tempo desde o último login
    public long getTempoDesdeUltimoLogin(int usuarioId) {
        return sessaoUsuarioService.minutosDesdeUltimoLogin(usuarioId);
    }

    // Método para criar um novo usuário
    public void criarUsuario(UsuarioDto usuario) throws SQLException, ClassNotFoundException {
        usuarioDao.cadastrarUsuario(usuario); // Chama o método do DAO para cadastrar
    }


    // Método para deletar um usuário
    public void deletarUsuario(int id) throws SQLException, ClassNotFoundException {
        usuarioDao.deletarUsuario(id);
    }
}
