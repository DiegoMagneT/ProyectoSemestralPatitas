package cl.dsy1103.ms_refugios.controller;

import cl.dsy1103.ms_refugios.DTO.RefugioRequestDTO;
import cl.dsy1103.ms_refugios.DTO.RefugioResponseDTO;
import cl.dsy1103.ms_refugios.service.RefugioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refugios")
@RequiredArgsConstructor
public class RefugioController {

    private final RefugioService refugioService;

    @GetMapping
    public ResponseEntity<List<RefugioResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(refugioService.obtenerTodosActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefugioResponseDTO> obtenerPorId(@PathVariable("id") Long id) {
        return refugioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<RefugioResponseDTO>> filtrarPorCiudad(@PathVariable("ciudad") String ciudad) {
        return ResponseEntity.ok(refugioService.filtrarPorCiudad(ciudad));
    }

    @PostMapping
    public ResponseEntity<RefugioResponseDTO> crear(@Valid @RequestBody RefugioRequestDTO dto) {
        return ResponseEntity.status(201).body(refugioService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RefugioResponseDTO> actualizar(
            @PathVariable("id") Long id,
            @Valid @RequestBody RefugioRequestDTO dto) {
        return refugioService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        if (refugioService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        refugioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/capacidad")
    public ResponseEntity<Integer> consultarCapacidad(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(refugioService.consultarCapacidadDisponible(id));
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}