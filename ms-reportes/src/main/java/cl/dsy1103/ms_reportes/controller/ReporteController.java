package cl.dsy1103.ms_reportes.controller;

import cl.dsy1103.ms_reportes.DTO.ReporteRequestDTO;
import cl.dsy1103.ms_reportes.DTO.ReporteResponseDTO;
import cl.dsy1103.ms_reportes.model.TipoReporte;
import cl.dsy1103.ms_reportes.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> obtenerTodosActivos() {
        return ResponseEntity.ok(reporteService.obtenerActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> obtenerPorId(@PathVariable Long id) {
        return reporteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/comuna/{comuna}")
    public ResponseEntity<List<ReporteResponseDTO>> filtrarPorComuna(@PathVariable String comuna) {
        return ResponseEntity.ok(reporteService.filtrarPorComuna(comuna));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ReporteResponseDTO>> filtrarPorTipo(@PathVariable TipoReporte tipo) {
        return ResponseEntity.ok(reporteService.filtrarPorTipo(tipo));
    }

    @PostMapping
    public ResponseEntity<ReporteResponseDTO> crear(@Valid @RequestBody ReporteRequestDTO dto) {
        return ResponseEntity.status(201).body(reporteService.guardar(dto));
    }

    @PutMapping("/{id}/resolver")
    public ResponseEntity<ReporteResponseDTO> resolver(@PathVariable Long id) {
        return reporteService.resolverReporte(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}