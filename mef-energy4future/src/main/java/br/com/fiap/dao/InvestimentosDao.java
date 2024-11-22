package br.com.fiap.dao;

import br.com.fiap.dto.InvestimentosDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvestimentosDao {
    private Connection connection;

    public InvestimentosDao(Connection connection) {
        this.connection = connection;
    }

    public void create(InvestimentosDto investimento) throws SQLException {
        String sql = "INSERT INTO t_investimentos (id, populacao, valor_invest, retorno, retorno_mes, tempo_preparo) VALUES (seq_t_investimentos_id.NEXTVAL, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, investimento.getPopulacao());
            stmt.setDouble(2, investimento.getValorInvest());
            stmt.setDouble(3, investimento.getRetorno());
            stmt.setDouble(4, investimento.getRetornoMes());
            stmt.setInt(5, investimento.getTempoPreparo());
            stmt.executeUpdate();
        }
    }


    public InvestimentosDto read(int id) throws SQLException {
        String sql = "SELECT * FROM t_investimentos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new InvestimentosDto(
                            rs.getInt("id"),
                            rs.getInt("populacao"),
                            rs.getDouble("valor_invest"),
                            rs.getDouble("retorno"),
                            rs.getDouble("retorno_mes"),
                            rs.getInt("tempo_preparo")
                    );
                }
            }
        }
        return null;
    }

    public List<InvestimentosDto> readAll() throws SQLException {
        List<InvestimentosDto> investimentos= new ArrayList<>();
        String sql = "select * from t_investimentos";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                investimentos.add(new InvestimentosDto(
                        rs.getInt("id"),
                        rs.getInt("populacao"),
                        rs.getDouble("valor_invest"),
                        rs.getDouble("retorno"),
                        rs.getDouble("retorno_mes"),
                        rs.getInt("tempo_preparo")
                ));
            }
        }
        return investimentos;
    }

    public void update(InvestimentosDto investimento) throws SQLException {
        String sql = "UPDATE t_investimentos SET populacao = ?, valor_invest = ?, retorno = ?, retorno_mes = ?, tempo_preparo = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, investimento.getPopulacao());
            stmt.setDouble(2, investimento.getValorInvest());
            stmt.setDouble(3, investimento.getRetorno());
            stmt.setDouble(4, investimento.getRetornoMes());
            stmt.setInt(5, investimento.getTempoPreparo());
            stmt.setInt(6, investimento.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM t_investimentos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
