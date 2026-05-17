package cl.dsy1103.ms_reportes.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteResponseDTO {
    private Long id;
    private Long usuarioId;
    private String titulo;
    private String tipoReporte;
    private String comuna;
    private String descripcion;
    private String contactoEmergencia;
    private String estado;
    private LocalDateTime fechaReporte;
}