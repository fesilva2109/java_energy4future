package br.com.fiap.dao;

import br.com.fiap.dto.UsuarioDto;
import br.com.fiap.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    // Método para inserir um novo usuário, utilizando a sequência para gerar o ID
    public void cadastrarUsuario(UsuarioDto usuario) throws SQLException, ClassNotFoundException {
        // Verificar se o email já existe
        String verificarEmailSql = "SELECT COUNT(*) FROM t_usuario WHERE email = ?";
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(verificarEmailSql)) {

            stmt.setString(1, usuario.getEmail());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("Email já cadastrado.");
                }
            }
        }

        // Inserir o usuário
        String sql = "INSERT INTO t_usuario (id, email, senha) VALUES (seq_t_usuario_id.nextval, ?, ?)";
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());
            stmt.executeUpdate();
        }
    }


    // Método para buscar um usuário pelo ID
    public UsuarioDto buscarPorId(int id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM t_usuario WHERE id = ?";
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UsuarioDto(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                }
            }
        }
        return null;  // Retorna null caso o usuário não seja encontrado
    }

    // Método para buscar um usuário pelo email e senha
    public UsuarioDto buscarPorEmailSenha(String email, String senha) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM t_usuario WHERE email = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UsuarioDto(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                }
            }
        }
        return null;  // Retorna null caso o usuário não seja encontrado
    }

    // Método para atualizar as informações de um usuário
    public void atualizarUsuario(UsuarioDto usuario) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE t_usuario SET email = ?, senha = ? WHERE id = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());
            stmt.setInt(3, usuario.getId());
            stmt.executeUpdate();
        }
    }

    // Método para deletar um usuário pelo ID
    public void deletarUsuario(int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM t_usuario WHERE id = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Método para listar todos os usuários
    public List<UsuarioDto> listarUsuarios() throws SQLException, ClassNotFoundException {
        List<UsuarioDto> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM t_usuario";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(new UsuarioDto(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("senha")
                ));
            }
        }
        return usuarios;
    }
}
