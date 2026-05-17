package cl.dsy1103.ms_adopciones.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdopcionRequestDTO {

    @NotNull(message = "El ID del adoptante solicitante es obligatorio")
    private Long solicitanteId;

    @NotNull(message = "El ID del animal a adoptar es obligatorio")
    private Long mascotaId;

    @NotNull(message = "El ID del refugio de origen es obligatorio")
    private Long refugioId;
}