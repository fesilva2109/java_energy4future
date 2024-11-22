package br.com.fiap.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Investimentos {
    private int id;
    private int populacao;
    private double valorInvest;
    private double retorno;
    private double retornoMes;
    private int tempoPreparo;
}
