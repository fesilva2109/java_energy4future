package br.com.fiap.test.TesteUsuarios;

import br.com.fiap.dao.UsuarioDao;
import br.com.fiap.dto.UsuarioDto;
import br.com.fiap.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestClasses {
    public static void main(String[] args) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            // Inicializando o DAO de Usuário
            UsuarioDao usuarioDao = new UsuarioDao();

            // Teste 1: Testando o Cadastro de Usuário
            System.out.println("=== Testando Cadastro de Usuário ===");
            UsuarioDto novoUsuario = new UsuarioDto(4, "usuario_teste@example.com", "senha123");

            // Verificar se o usuário já existe
            UsuarioDto usuarioExistente = usuarioDao.buscarPorEmailSenha(novoUsuario.getEmail(), novoUsuario.getSenha());
            if (usuarioExistente == null) {
                usuarioDao.cadastrarUsuario(novoUsuario);
                System.out.println("Usuário cadastrado com sucesso!");
            } else {
                System.out.println("Usuário já existe.");
            }

            // Teste 2: Testando a Consulta por ID
            System.out.println("\n=== Testando Consulta de Usuário por ID ===");
            UsuarioDto usuarioBuscado = usuarioDao.buscarPorId(novoUsuario.getId());
            if (usuarioBuscado != null) {
                System.out.println("Usuário encontrado: " + usuarioBuscado.getEmail());
            } else {
                System.out.println("Usuário não encontrado.");
            }

            // Teste 3: Testando a Consulta por Email
            System.out.println("\n=== Testando Consulta de Usuário por Email ===");
            UsuarioDto usuarioPorEmail = usuarioDao.buscarPorEmailSenha("usuario_teste@example.com", "senha123");
            if (usuarioPorEmail != null) {
                System.out.println("Usuário encontrado: " + usuarioPorEmail.getEmail());
            } else {
                System.out.println("Usuário não encontrado.");
            }

            // Teste 4: Testando a Atualização de um Usuário
            System.out.println("\n=== Testando Atualização de Usuário ===");
            usuarioBuscado.setEmail("novo_email@example.com");
            usuarioDao.atualizarUsuario(usuarioBuscado);

            UsuarioDto usuarioAtualizado = usuarioDao.buscarPorId(usuarioBuscado.getId());
            if (usuarioAtualizado != null && usuarioAtualizado.getEmail().equals("novo_email@example.com")) {
                System.out.println("Usuário atualizado com sucesso!");
            } else {
                System.out.println("Falha ao atualizar usuário.");
            }

            // Teste 5: Testando a Exclusão de um Usuário
            System.out.println("\n=== Testando Exclusão de Usuário ===");
            usuarioDao.deletarUsuario(usuarioBuscado.getId());

            UsuarioDto usuarioDeletado = usuarioDao.buscarPorId(usuarioBuscado.getId());
            if (usuarioDeletado == null) {
                System.out.println("Usuário deletado com sucesso!");
            } else {
                System.out.println("Falha ao deletar usuário.");
            }

            // Teste 6: Testando Listagem de Todos os Usuários
            System.out.println("\n=== Testando Listagem de Todos os Usuários ===");
            List<UsuarioDto> listaUsuarios = usuarioDao.listarUsuarios();
            for (UsuarioDto u : listaUsuarios) {
                System.out.println("Usuário: " + u.getEmail());
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
