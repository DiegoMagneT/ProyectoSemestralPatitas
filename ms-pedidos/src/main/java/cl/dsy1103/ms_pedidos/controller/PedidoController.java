package cl.dsy1103.ms_pedidos.controller;

import cl.dsy1103.ms_pedidos.DTO.PedidoRequestDTO;
import cl.dsy1103.ms_pedidos.DTO.PedidoResponseDTO;
import cl.dsy1103.ms_pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(pedidoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return pedidoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/comprador/{compradorId}")
    public ResponseEntity<List<PedidoResponseDTO>> obtenerPorComprador(@PathVariable Long compradorId) {
        return ResponseEntity.ok(pedidoService.obtenerPorComprador(compradorId));
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crear(@Valid @RequestBody PedidoRequestDTO dto) {
        return ResponseEntity.status(201).body(pedidoService.guardar(dto));
    }
}