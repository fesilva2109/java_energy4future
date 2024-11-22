package br.com.fiap.test.TesteLocalidades;


import br.com.fiap.dao.LocalidadeDao;
import br.com.fiap.dto.LocalidadeDto;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.service.LocalidadeService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestClasses {
    public static void main(String[] args) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            // Inicializar o DAO de Localidade
            LocalidadeDao localidadeDao = new LocalidadeDao(connection);
            LocalidadeService localidadeService = new LocalidadeService(localidadeDao);

            // Testar Criação de Localidade
            System.out.println("=== Testando Criação de Localidade ===");
            LocalidadeDto localidade = new LocalidadeDto(0, 100000, "Rua Teste", "Bairro Teste", "Cidade Teste", "SP", "12345-678");
            localidadeService.addLocalidade(localidade);
            System.out.println("Localidade criada com sucesso!");

            // Testar Leitura de Localidade por ID
            System.out.println("=== Testando Leitura de Localidade por ID ===");
            LocalidadeDto localidadeLida = localidadeService.getLocalidadeById(1);
            if (localidadeLida != null) {
                System.out.println("Localidade encontrada: " + localidadeLida);
            } else {
                System.out.println("Localidade não encontrada.");
            }

            // Testar Leitura de Todas as Localidades
            System.out.println("=== Testando Leitura de Todas as Localidades ===");
            List<LocalidadeDto> localidades = localidadeService.getAllLocalidades();
            for (LocalidadeDto loc : localidades) {
                System.out.println(loc);
            }

            // Testar Atualização de Localidade
            System.out.println("=== Testando Atualização de Localidade ===");
            localidade.setLogradouro("Rua Teste Atualizada");
            localidadeService.updateLocalidade(localidade.getId(), localidade);
            System.out.println("Localidade atualizada!");

            // Testar Exclusão de Localidade
            System.out.println("=== Testando Exclusão de Localidade ===");
            localidadeService.deleteLocalidade(localidade.getId());
            System.out.println("Localidade excluída!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
