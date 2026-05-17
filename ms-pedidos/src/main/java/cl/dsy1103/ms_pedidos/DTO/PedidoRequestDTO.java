package cl.dsy1103.ms_pedidos.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {

    @NotNull(message = "El ID del comprador es obligatorio")
    private Long compradorId;

    @NotNull(message = "El ID de la publicación es obligatorio")
    private Long publicacionId;
}