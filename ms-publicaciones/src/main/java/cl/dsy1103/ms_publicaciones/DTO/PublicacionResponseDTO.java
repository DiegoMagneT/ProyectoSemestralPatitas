package cl.dsy1103.ms_publicaciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicacionResponseDTO {
    private Long id;
    private Long vendedorId;
    private String titulo;
    private String descripcion;
    private BigDecimal precio;
    private String estadoProducto;
    private String categoria;
    private Boolean disponible;
    private LocalDateTime fechaPublicacion;
}