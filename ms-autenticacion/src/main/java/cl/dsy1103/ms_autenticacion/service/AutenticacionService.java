package cl.dsy1103.ms_autenticacion.service;

import cl.dsy1103.ms_autenticacion.client.UsuarioClient;
import cl.dsy1103.ms_autenticacion.DTO.LoginRequestDTO;
import cl.dsy1103.ms_autenticacion.DTO.RegistroRequestDTO;
import cl.dsy1103.ms_autenticacion.DTO.TokenResponseDTO;
import cl.dsy1103.ms_autenticacion.model.Credencial;
import cl.dsy1103.ms_autenticacion.repository.CredencialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AutenticacionService {

    private final CredencialRepository credencialRepository;
    private final UsuarioClient usuarioClient;

    public String registrarCredenciales(RegistroRequestDTO dto) {
        if (credencialRepository.existsByUsuarioId(dto.getUsuarioId())) {
            throw new RuntimeException("El usuario ya tiene credenciales registradas");
        }


        Boolean activo = usuarioClient.verificarUsuarioActivo(dto.getUsuarioId());

        if (activo == null || !activo) {
            throw new RuntimeException("El usuario se encuentra inactivo, no se pueden registrar credenciales");
        }


        Credencial credencial = new Credencial();
        credencial.setUsuarioId(dto.getUsuarioId());
        credencial.setPasswordHash(dto.getPassword());
        credencial.setRol(dto.getRol());

        credencialRepository.save(credencial);
        return "Credenciales registradas con éxito para el usuario ID: " + dto.getUsuarioId();
    }

    public TokenResponseDTO login(LoginRequestDTO dto) {
        Credencial credencial = credencialRepository.findByUsuarioId(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Credenciales no encontradas"));

        if (!credencial.getPasswordHash().equals(dto.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }


        credencial.setUltimoLogin(LocalDateTime.now());
        credencialRepository.save(credencial);


        String tokenSimulado = "jwt-token-simulado-para-" + credencial.getUsuarioId() + "-rol-" + credencial.getRol();
        return new TokenResponseDTO(tokenSimulado);
    }
}