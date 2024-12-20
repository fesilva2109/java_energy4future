package br.com.fiap.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoUsuario {
    private int loginId;
    private int usuarioId;
    private LocalDateTime timestampLogin;
}
