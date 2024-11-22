package br.com.fiap.test.TesteInvestimentos;

import br.com.fiap.dao.InvestimentosDao;
import br.com.fiap.dto.InvestimentosDto;
import br.com.fiap.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestClasses {
    public static void main(String[] args) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            // Inicializar o DAO de Investimentos
            InvestimentosDao investimentosDao = new InvestimentosDao(connection);

            // Testar Criação de Investimento
            System.out.println("=== Testando Criação de Investimento ===");
            InvestimentosDto novoInvestimento = new InvestimentosDto(0, 5000, 10000.0, 15000.0, 200.0, 6);
            investimentosDao.create(novoInvestimento);
            System.out.println("Investimento criado com sucesso!");

            // Testar Leitura de Investimento por ID
            System.out.println("=== Testando Leitura de Investimento por ID ===");
            int idTestar = 1; // Atualize conforme necessário
            InvestimentosDto investimentoLido = investimentosDao.read(idTestar);
            if (investimentoLido != null) {
                System.out.println("Investimento encontrado: " + investimentoLido);
            } else {
                System.out.println("Investimento não encontrado para ID: " + idTestar);
            }

            // Testar Leitura de Todos os Investimentos
            System.out.println("=== Testando Leitura de Todos os Investimentos ===");
            List<InvestimentosDto> todosInvestimentos = investimentosDao.readAll();
            if (!todosInvestimentos.isEmpty()) {
                todosInvestimentos.forEach(System.out::println);
            } else {
                System.out.println("Nenhum investimento encontrado.");
            }

            // Testar Atualização de Investimento
            System.out.println("=== Testando Atualização de Investimento ===");
            if (investimentoLido != null) {
                investimentoLido.setValorInvest(20000.0); // Alterando o valor do investimento
                investimentosDao.update(investimentoLido);
                System.out.println("Investimento atualizado: " + investimentoLido);
            } else {
                System.out.println("Atualização não realizada: investimento não encontrado.");
            }

            // Testar Exclusão de Investimento
            System.out.println("=== Testando Exclusão de Investimento ===");
            if (investimentoLido != null) {
                investimentosDao.delete(investimentoLido.getId());
                System.out.println("Investimento excluído com sucesso: ID " + investimentoLido.getId());
            } else {
                System.out.println("Exclusão não realizada: investimento não encontrado.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
