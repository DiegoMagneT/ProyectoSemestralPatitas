package cl.dsy1103.ms_mascotas.controller;

import cl.dsy1103.ms_mascotas.DTO.MascotaRequestDTO;
import cl.dsy1103.ms_mascotas.DTO.MascotaResponseDTO;
import cl.dsy1103.ms_mascotas.model.Especie;
import cl.dsy1103.ms_mascotas.model.EstadoMascota;
import cl.dsy1103.ms_mascotas.service.MascotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @GetMapping
    public ResponseEntity<List<MascotaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(mascotaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return mascotaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/refugio/{id}")
    public ResponseEntity<List<MascotaResponseDTO>> obtenerPorRefugio(@PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.obtenerPorRefugio(id));
    }

    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<MascotaResponseDTO>> obtenerPorEspecie(@PathVariable Especie especie) {
        return ResponseEntity.ok(mascotaService.obtenerPorEspecie(especie));
    }

    @PostMapping
    public ResponseEntity<MascotaResponseDTO> crear(@Valid @RequestBody MascotaRequestDTO dto) {
        return ResponseEntity.status(201).body(mascotaService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MascotaRequestDTO dto) {
        return mascotaService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<MascotaResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam EstadoMascota estado) {
        return mascotaService.cambiarEstado(id, estado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}