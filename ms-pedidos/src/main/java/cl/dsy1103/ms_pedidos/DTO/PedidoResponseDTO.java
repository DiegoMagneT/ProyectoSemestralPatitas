package cl.dsy1103.ms_pedidos.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {
    private Long id;
    private Long compradorId;
    private Long publicacionId;
    private BigDecimal total;
    private String estado;
    private LocalDateTime fechaPedido;
}
