package cl.dsy1103.ms_usuarios.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "Debe tener un formato de correo válido")
    private String email;

    private String telefono;
}
