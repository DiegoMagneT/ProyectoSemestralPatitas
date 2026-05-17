package cl.dsy1103.ms_usuarios.service;

import cl.dsy1103.ms_usuarios.DTO.UsuarioRequestDTO;
import cl.dsy1103.ms_usuarios.DTO.UsuarioResponseDTO;
import cl.dsy1103.ms_usuarios.model.Usuario;
import cl.dsy1103.ms_usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private UsuarioResponseDTO mapToDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getActivo(),
                usuario.getFechaRegistro()
        );
    }

    public List<UsuarioResponseDTO> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> mapToDTO(usuario))
                .collect(Collectors.toList());
    }

    public Optional<UsuarioResponseDTO> obtenerPorId(Long id) {
        return usuarioRepository.findById(id).map(usuario -> mapToDTO(usuario));
    }

    public UsuarioResponseDTO guardar(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El correo electrónico ya se encuentra registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setActivo(true);

        return mapToDTO(usuarioRepository.save(usuario));
    }

    public Optional<UsuarioResponseDTO> actualizar(Long id, UsuarioRequestDTO dto) {
        return usuarioRepository.findById(id).map(existente -> {

            if (!existente.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
                throw new RuntimeException("El correo electrónico ya se encuentra registrado por otro usuario");
            }
            existente.setNombre(dto.getNombre());
            existente.setEmail(dto.getEmail());
            existente.setTelefono(dto.getTelefono());
            return mapToDTO(usuarioRepository.save(existente));
        });
    }


    public void eliminar(Long id) {
        usuarioRepository.findById(id).ifPresent(usuario -> {
            usuario.setActivo(false);
            usuarioRepository.save(usuario);
        });
    }


    public boolean verificarActivo(Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> usuario.getActivo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }
}