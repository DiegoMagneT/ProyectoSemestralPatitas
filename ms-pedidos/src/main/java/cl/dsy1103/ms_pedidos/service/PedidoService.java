package cl.dsy1103.ms_pedidos.service;

import cl.dsy1103.ms_pedidos.client.MarketplaceOrquestadorClient;
import cl.dsy1103.ms_pedidos.DTO.PedidoRequestDTO;
import cl.dsy1103.ms_pedidos.DTO.PedidoResponseDTO;
import cl.dsy1103.ms_pedidos.model.EstadoPedido;
import cl.dsy1103.ms_pedidos.model.Pedido;
import cl.dsy1103.ms_pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MarketplaceOrquestadorClient orquestadorClient;

    private PedidoResponseDTO mapToDTO(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getCompradorId(),
                pedido.getPublicacionId(),
                pedido.getTotal(),
                pedido.getEstado().name(),
                pedido.getFechaPedido()
        );
    }

    public List<PedidoResponseDTO> obtenerTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedido -> mapToDTO(pedido))
                .collect(Collectors.toList());
    }

    public Optional<PedidoResponseDTO> obtenerPorId(Long id) {
        return pedidoRepository.findById(id).map(pedido -> mapToDTO(pedido));
    }

    public List<PedidoResponseDTO> obtenerPorComprador(Long compradorId) {
        return pedidoRepository.findByCompradorId(compradorId)
                .stream()
                .map(pedido -> mapToDTO(pedido))
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoResponseDTO guardar(PedidoRequestDTO dto) {

        Boolean compradorActivo = orquestadorClient.verificarCompradorActivo(dto.getCompradorId());
        if (compradorActivo == null || !compradorActivo) {
            throw new RuntimeException("La cuenta del comprador no se encuentra activa para realizar transacciones");
        }


        Map<String, Object> publicacion = orquestadorClient.obtenerDetallePublicacion(dto.getPublicacionId());
        if (publicacion == null || !(Boolean) publicacion.get("disponible")) {
            throw new RuntimeException("El artículo seleccionado ya no se encuentra disponible para la venta");
        }


        Long vendedorId = ((Number) publicacion.get("vendedorId")).longValue();
        if (vendedorId.equals(dto.getCompradorId())) {
            throw new RuntimeException("No puedes comprar un artículo publicado por ti mismo");
        }


        BigDecimal precioArticulo = new BigDecimal(publicacion.get("precio").toString());

        Pedido pedido = new Pedido();
        pedido.setCompradorId(dto.getCompradorId());
        pedido.setPublicacionId(dto.getPublicacionId());
        pedido.setTotal(precioArticulo);
        pedido.setEstado(EstadoPedido.PROCESADO);


        orquestadorClient.marcarComoVendidoRemoto(dto.getPublicacionId());

        return mapToDTO(pedidoRepository.save(pedido));
    }
}