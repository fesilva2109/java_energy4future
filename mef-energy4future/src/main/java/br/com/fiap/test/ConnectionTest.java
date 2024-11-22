package br.com.fiap.test;

import br.com.fiap.factory.ConnectionFactory;

import java.sql.Connection;

public class ConnectionTest{

    public static void main(String[] args) {
        //Obter uma conexão com o banco de dados
        try {
            Connection conexao = ConnectionFactory.getConnection();
            System.out.println("Conexão com o banco obtido com sucesso!");
            conexao.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
