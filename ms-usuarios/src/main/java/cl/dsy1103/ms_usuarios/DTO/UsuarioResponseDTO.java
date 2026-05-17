package cl.dsy1103.ms_usuarios.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private Boolean activo;
    private LocalDateTime fechaRegistro;
}