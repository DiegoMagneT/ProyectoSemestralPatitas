package cl.dsy1103.ms_adopciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdopcionResponseDTO {
    private Long id;
    private Long solicitanteId;
    private Long mascotaId;
    private Long refugioId;
    private String estado;
    private LocalDateTime fechaSolicitud;
    private String motivoRechazo;
}