package br.com.fiap.dao;

import br.com.fiap.models.SessaoUsuario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessaoUsuarioDao {
    private Connection connection;

    // Construtor da classe que inicializa a conexão com o banco de dados
    public SessaoUsuarioDao(Connection connection) {
        this.connection = connection;
    }

    // Método para criar uma nova sessão de usuário
    public void create(SessaoUsuario sessaoUsuario) throws SQLException {
        // Usando a sequência para gerar o loginId
        String sql = "INSERT INTO t_sessao_usuario (login_id, usuario_id, timestamp_login) " +
                "VALUES ( seq_t_sessao_usuario_id.NEXTVAL, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sessaoUsuario.getUsuarioId());  // Definindo o usuario_id
            stmt.setTimestamp(2, Timestamp.valueOf(sessaoUsuario.getTimestampLogin()));

            // Executa a inserção, a sequence cuida de gerar o loginId automaticamente
            stmt.executeUpdate();
        }
    }

    // Método para buscar uma sessão por loginId
    public SessaoUsuario read(int loginId) throws SQLException {
        String sql = "SELECT * FROM t_sessao_usuario WHERE login_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, loginId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractSessaoUsuarioFromResultSet(rs);
                }
            }
        }
        return null;
    }

    // Método para listar todas as sessões
    public List<SessaoUsuario> readAll() throws SQLException {
        List<SessaoUsuario> sessoes = new ArrayList<>();
        String sql = "SELECT * FROM t_sessao_usuario";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sessoes.add(extractSessaoUsuarioFromResultSet(rs));
            }
        }
        return sessoes;
    }

    // Método para atualizar uma sessão
    public void update(SessaoUsuario sessaoUsuario) throws SQLException {
        String sql = "UPDATE t_sessao_usuario SET usuario_id = ?, timestamp_login = ? WHERE login_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sessaoUsuario.getUsuarioId());
            stmt.setTimestamp(2, Timestamp.valueOf(sessaoUsuario.getTimestampLogin()));
            stmt.setInt(3, sessaoUsuario.getLoginId());
            stmt.executeUpdate();
        }
    }

    // Método para deletar uma sessão pelo loginId
    public void delete(int loginId) throws SQLException {
        String sql = "DELETE FROM t_sessao_usuario WHERE login_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, loginId);
            stmt.executeUpdate();
        }
    }

    // Método para buscar uma sessão por usuarioId
    public SessaoUsuario findByUsuarioId(int usuarioId) throws SQLException {
        String sql = "SELECT * FROM t_sessao_usuario WHERE usuario_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractSessaoUsuarioFromResultSet(rs);
                }
            }
        }
        return null;
    }

    // Método auxiliar para extrair um objeto SessaoUsuario de um ResultSet
    private SessaoUsuario extractSessaoUsuarioFromResultSet(ResultSet rs) throws SQLException {
        LocalDateTime timestampLogin = rs.getTimestamp("timestamp_login") != null
                ? rs.getTimestamp("timestamp_login").toLocalDateTime()
                : null;

        return new SessaoUsuario(
                rs.getInt("login_id"),
                rs.getInt("usuario_id"),
                timestampLogin
        );
    }
}
