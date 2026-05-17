package cl.dsy1103.ms_autenticacion.controller;

import cl.dsy1103.ms_autenticacion.DTO.LoginRequestDTO;
import cl.dsy1103.ms_autenticacion.DTO.RegistroRequestDTO;
import cl.dsy1103.ms_autenticacion.DTO.TokenResponseDTO;
import cl.dsy1103.ms_autenticacion.service.AutenticacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacionController {

    private final AutenticacionService autenticacionService;

    @PostMapping("/registro")
    public ResponseEntity<String> registrar(@Valid @RequestBody RegistroRequestDTO dto) {
        String resultado = autenticacionService.registrarCredenciales(dto);
        return ResponseEntity.status(201).body(resultado);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(autenticacionService.login(dto));
    }
}