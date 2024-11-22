package br.com.fiap.dao;

import br.com.fiap.dto.LocalidadeDto;
import br.com.fiap.models.Localidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocalidadeDao {
    private Connection connection;

    public LocalidadeDao(Connection connection) {
        this.connection = connection;
    }

    // Método para criar uma nova localidade
    public void create(Localidade localidade) throws SQLException {
        String sql = "INSERT INTO t_localidade (id, populacao, logradouro, bairro, cidade, estado, cep) VALUES (seq_t_localidade_id.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, localidade.getPopulacao());
            stmt.setString(2, localidade.getLogradouro());
            stmt.setString(3, localidade.getBairro());
            stmt.setString(4, localidade.getCidade());
            stmt.setString(5, localidade.getEstado());
            stmt.setString(6, localidade.getCep());
            stmt.executeUpdate();
        }
    }

    // Método para ler uma localidade pelo ID
    public Localidade read(int id) throws SQLException {
        String sql = "SELECT * FROM t_localidade WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Localidade(
                            rs.getInt("id"),
                            rs.getInt("populacao"),
                            rs.getString("logradouro"),
                            rs.getString("bairro"),
                            rs.getString("cidade"),
                            rs.getString("estado"),
                            rs.getString("cep")
                    );
                }
            }
        }
        return null;
    }

    // Método para ler todas as localidades
    public List<LocalidadeDto> readAll() throws SQLException {
        List<LocalidadeDto> localidades = new ArrayList<>();
        String sql = "SELECT * FROM t_localidade";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                localidades.add(new LocalidadeDto(
                        rs.getInt("id"),
                        rs.getInt("populacao"),
                        rs.getString("logradouro"),
                        rs.getString("bairro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("cep")
                ));
            }
        }
        return localidades;
    }

    // Método para atualizar uma localidade
    public void update(Localidade localidade) throws SQLException {
        String sql = "UPDATE t_localidade SET populacao = ?, logradouro = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, localidade.getPopulacao());
            stmt.setString(2, localidade.getLogradouro());
            stmt.setString(3, localidade.getBairro());
            stmt.setString(4, localidade.getCidade());
            stmt.setString(5, localidade.getEstado());
            stmt.setString(6, localidade.getCep());
            stmt.setInt(7, localidade.getId());
            stmt.executeUpdate();
        }
    }

    // Método para deletar uma localidade pelo ID
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM t_localidade WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
