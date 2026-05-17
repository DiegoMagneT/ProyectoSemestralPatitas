package cl.dsy1103.ms_autenticacion.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotNull(message = "El ID de usuario es obligatorio para iniciar sesión")
    private Long usuarioId;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}