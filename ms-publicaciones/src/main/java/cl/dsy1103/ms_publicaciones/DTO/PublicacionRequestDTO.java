package cl.dsy1103.ms_publicaciones.DTO;

import cl.dsy1103.ms_publicaciones.model.EstadoProducto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicacionRequestDTO {

    @NotNull(message = "El ID del vendedor es obligatorio")
    private Long vendedorId;

    @NotBlank(message = "El título de la publicación no puede estar vacío")
    private String titulo;

    @NotBlank(message = "La descripción del producto es obligatoria")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    private BigDecimal precio;

    @NotNull(message = "El estado del producto es obligatorio (NUEVO, USADO)")
    private EstadoProducto estadoProducto;

    @NotBlank(message = "La categoría del artículo es obligatoria")
    private String categoria;
}