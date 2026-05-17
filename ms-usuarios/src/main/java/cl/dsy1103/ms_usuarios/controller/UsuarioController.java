package cl.dsy1103.ms_usuarios.controller;

import cl.dsy1103.ms_usuarios.DTO.UsuarioRequestDTO;
import cl.dsy1103.ms_usuarios.DTO.UsuarioResponseDTO;
import cl.dsy1103.ms_usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO nuevo = usuarioService.guardar(dto);
        return ResponseEntity.status(201).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return usuarioService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (usuarioService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/activo")
    public ResponseEntity<Boolean> verificarActivo(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.verificarActivo(id));
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}