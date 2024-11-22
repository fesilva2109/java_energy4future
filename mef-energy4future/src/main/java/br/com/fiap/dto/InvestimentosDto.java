package br.com.fiap.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvestimentosDto {
    private int id;
    private int populacao;
    private double valorInvest;
    private double retorno;
    private double retornoMes;
    private int tempoPreparo;

    // Cálculo do retorno anual com base no retorno mensal
    public double calcularRetornoAnual() {
        return retornoMes * 12;
    }

    // Validação dos campos de investimento
    public void validarInvestimento() {
        if (valorInvest <= 0) throw new IllegalArgumentException("Valor do investimento deve ser maior que zero");
        if (retorno < 0) throw new IllegalArgumentException("Retorno não pode ser negativo");
        if (tempoPreparo <= 0) throw new IllegalArgumentException("Tempo de preparo deve ser maior que zero");
    }
}
