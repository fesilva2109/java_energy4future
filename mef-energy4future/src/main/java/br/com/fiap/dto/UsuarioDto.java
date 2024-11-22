package br.com.fiap.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDto {
    private int id;
    private String email;
    private String senha;

    // Validação de email e senha
    public void validarUsuario() {
        if (email == null || !email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$")) {
            throw new IllegalArgumentException("Email inválido");
        }

    }
}
