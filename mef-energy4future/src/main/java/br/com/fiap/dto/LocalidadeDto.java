package br.com.fiap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocalidadeDto {
    private int id;
    private int populacao;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    // Lógica de negócio: cálculo de densidade populacional
    public double calcularDensidadePopulacional(double areaKm2) {
        if (areaKm2 <= 0) {
            throw new IllegalArgumentException("A área deve ser maior que zero");
        }
        return populacao / areaKm2;
    }

    // Validação dos campos obrigatórios
    public void validarLocalidade() {
        if (populacao <= 0) throw new IllegalArgumentException("População deve ser maior que zero");
        if (logradouro == null || logradouro.isEmpty()) throw new IllegalArgumentException("Logradouro é obrigatório");
        if (cidade == null || cidade.isEmpty()) throw new IllegalArgumentException("Cidade é obrigatória");
        if (estado == null || estado.length() != 2) throw new IllegalArgumentException("Estado deve ter dois caracteres");
    }
}
