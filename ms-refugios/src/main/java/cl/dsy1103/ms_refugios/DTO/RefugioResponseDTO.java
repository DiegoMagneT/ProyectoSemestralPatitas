package cl.dsy1103.ms_refugios.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefugioResponseDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private Integer capacidadMax;
    private Integer capacidadActual;
    private Long operadorId;
    private Boolean activo;
}