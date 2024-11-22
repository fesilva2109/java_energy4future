package br.com.fiap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessaoUsuarioDto {
    private int loginId;
    private int usuarioId;
    private LocalDateTime localDateTime;

    // Constantes para definir o tempo de expiração (ex: 30 minutos)
    private static final long TEMPO_EXPIRACAO_MINUTOS = 30;

    // Cálculo do tempo desde o último login
    public long calcularMinutosDesdeLogin() {
        return Duration.between(localDateTime, LocalDateTime.now()).toMinutes();
    }

    // Verifica se a sessão está expirada
    public boolean isSessaoExpirada() {
        long minutosDesdeLogin = calcularMinutosDesdeLogin();
        return minutosDesdeLogin > TEMPO_EXPIRACAO_MINUTOS;
    }

    // Atualiza o tempo da última atividade (para manter a sessão ativa)
    public void atualizarUltimaAtividade() {
        this.localDateTime = LocalDateTime.now();
    }

    // Validação do ID de usuário
    public void validarSessao() {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário deve ser válido e maior que zero");
        }
    }
}
