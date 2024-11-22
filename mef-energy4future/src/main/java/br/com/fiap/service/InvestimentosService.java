package br.com.fiap.service;

import br.com.fiap.dao.InvestimentosDao;
import br.com.fiap.dto.InvestimentosDto;

import java.sql.SQLException;
import java.util.List;


public class InvestimentosService {
    private final InvestimentosDao investimentosDao;

    public InvestimentosService(InvestimentosDao investimentosDao) {
        this.investimentosDao = investimentosDao;
    }

    public void createInvestment(InvestimentosDto investimentosDto) throws SQLException {
        investimentosDto.validarInvestimento(); // Validate DTO
        InvestimentosDto investimento = new InvestimentosDto(
                0, // ID is auto-incremented
                investimentosDto.getPopulacao(),
                investimentosDto.getValorInvest(),
                investimentosDto.getRetorno(),
                investimentosDto.getRetornoMes(),
                investimentosDto.getTempoPreparo()
        );
        investimentosDao.create(investimento);
    }

    public InvestimentosDto getInvestmentById(int id) throws SQLException {
        InvestimentosDto investimento = investimentosDao.read(id);
        if (investimento == null) {
            throw new IllegalArgumentException("Investment not found with ID: " + id);
        }
        return investimento;
    }

    public List<InvestimentosDto> getAllInvestments() throws SQLException {
        return investimentosDao.readAll();
    }

    public void updateInvestment(InvestimentosDto investimentosDto) throws SQLException {
        investimentosDto.validarInvestimento();
        InvestimentosDto investimento = new InvestimentosDto(
                investimentosDto.getId(),
                investimentosDto.getPopulacao(),
                investimentosDto.getValorInvest(),
                investimentosDto.getRetorno(),
                investimentosDto.getRetornoMes(),
                investimentosDto.getTempoPreparo()
        );
        investimentosDao.update(investimento);
    }

    public void deleteInvestment(int id) throws SQLException {
        investimentosDao.delete(id);
    }


}
