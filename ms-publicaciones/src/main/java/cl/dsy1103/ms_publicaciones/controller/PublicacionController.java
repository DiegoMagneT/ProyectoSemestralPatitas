package cl.dsy1103.ms_publicaciones.controller;

import cl.dsy1103.ms_publicaciones.DTO.PublicacionRequestDTO;
import cl.dsy1103.ms_publicaciones.DTO.PublicacionResponseDTO;
import cl.dsy1103.ms_publicaciones.service.PublicacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
@RequiredArgsConstructor
public class PublicacionController {

    private final PublicacionService publicacionService;

    @GetMapping
    public ResponseEntity<List<PublicacionResponseDTO>> obtenerTodasDisponibles() {
        return ResponseEntity.ok(publicacionService.obtenerDisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return publicacionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<PublicacionResponseDTO>> filtrarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(publicacionService.filtrarPorCategoria(categoria));
    }

    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<PublicacionResponseDTO>> obtenerPorVendedor(@PathVariable Long vendedorId) {
        return ResponseEntity.ok(publicacionService.obtenerPorVendedor(vendedorId));
    }

    @PostMapping
    public ResponseEntity<PublicacionResponseDTO> crear(@Valid @RequestBody PublicacionRequestDTO dto) {
        return ResponseEntity.status(201).body(publicacionService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacionResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PublicacionRequestDTO dto) {
        return publicacionService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (publicacionService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        publicacionService.desactivarPublicacion(id); // Soft delete
        return ResponseEntity.noContent().build();
    }
}
