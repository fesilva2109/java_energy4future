package br.com.fiap.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Localidade {
    private int id;
    private int populacao;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}
