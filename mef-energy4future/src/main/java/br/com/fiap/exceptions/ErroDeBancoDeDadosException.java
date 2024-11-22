package br.com.fiap.exceptions;

public class ErroDeBancoDeDadosException extends RuntimeException {
    public ErroDeBancoDeDadosException(String message) {
        super(message);
    }
}
