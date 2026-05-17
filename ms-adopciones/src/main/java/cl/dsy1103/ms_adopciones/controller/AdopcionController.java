package cl.dsy1103.ms_adopciones.controller;

import cl.dsy1103.ms_adopciones.DTO.AdopcionRequestDTO;
import cl.dsy1103.ms_adopciones.DTO.AdopcionResponseDTO;
import cl.dsy1103.ms_adopciones.service.AdopcionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/adopciones")
@RequiredArgsConstructor
public class AdopcionController {

    private final AdopcionService adopcionService;

    @GetMapping
    public ResponseEntity<List<AdopcionResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(adopcionService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdopcionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return adopcionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<AdopcionResponseDTO>> obtenerPorUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(adopcionService.obtenerPorUsuario(id));
    }

    @PostMapping
    public ResponseEntity<AdopcionResponseDTO> crear(@Valid @RequestBody AdopcionRequestDTO dto) {
        return ResponseEntity.status(201).body(adopcionService.guardar(dto));
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<AdopcionResponseDTO> aprobar(@PathVariable Long id) {
        return adopcionService.aprobarSolicitud(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<AdopcionResponseDTO> rechazar(
            @PathVariable Long id,
            @RequestParam String motivo) {
        return adopcionService.rechazarSolicitud(id, motivo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}