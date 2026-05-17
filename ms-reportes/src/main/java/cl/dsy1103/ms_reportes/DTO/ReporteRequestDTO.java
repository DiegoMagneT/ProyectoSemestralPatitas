package cl.dsy1103.ms_reportes.DTO;

import cl.dsy1103.ms_reportes.model.TipoReporte;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteRequestDTO {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @NotBlank(message = "El título del reporte no puede estar vacío")
    private String titulo;

    @NotNull(message = "El tipo de reporte es obligatorio (PERDIDO, AVISTADO)")
    private TipoReporte tipoReporte;

    @NotBlank(message = "La comuna es obligatoria para la búsqueda")
    private String comuna;

    @NotBlank(message = "La descripción de la mascota y situación es obligatoria")
    private String descripcion;

    @NotBlank(message = "Debe proporcionar un teléfono o medio de contacto")
    private String contactoEmergencia;
}