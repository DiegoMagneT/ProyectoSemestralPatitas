package cl.dsy1103.ms_publicaciones.service;

import cl.dsy1103.ms_publicaciones.client.UsuarioClient;
import cl.dsy1103.ms_publicaciones.DTO.PublicacionRequestDTO;
import cl.dsy1103.ms_publicaciones.DTO.PublicacionResponseDTO;
import cl.dsy1103.ms_publicaciones.model.Publicacion;
import cl.dsy1103.ms_publicaciones.repository.PublicacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final UsuarioClient usuarioClient;

    private PublicacionResponseDTO mapToDTO(Publicacion publicacion) {
        return new PublicacionResponseDTO(
                publicacion.getId(),
                publicacion.getVendedorId(),
                publicacion.getTitulo(),
                publicacion.getDescripcion(),
                publicacion.getPrecio(),
                publicacion.getEstadoProducto().name(),
                publicacion.getCategoria(),
                publicacion.getDisponible(),
                publicacion.getFechaPublicacion()
        );
    }

    public List<PublicacionResponseDTO> obtenerDisponibles() {
        return publicacionRepository.findByDisponibleTrue()
                .stream()
                .map(publicacion -> mapToDTO(publicacion))
                .collect(Collectors.toList());
    }

    public Optional<PublicacionResponseDTO> obtenerPorId(Long id) {
        return publicacionRepository.findById(id).map(publicacion -> mapToDTO(publicacion));
    }

    public List<PublicacionResponseDTO> filtrarPorCategoria(String categoria) {
        return publicacionRepository.findByCategoriaIgnoreCaseAndDisponibleTrue(categoria)
                .stream()
                .map(publicacion -> mapToDTO(publicacion))
                .collect(Collectors.toList());
    }

    public List<PublicacionResponseDTO> obtenerPorVendedor(Long vendedorId) {
        return publicacionRepository.findByVendedorId(vendedorId)
                .stream()
                .map(publicacion -> mapToDTO(publicacion))
                .collect(Collectors.toList());
    }

    public PublicacionResponseDTO guardar(PublicacionRequestDTO dto) {
        // Validación remota mediante WebClient
        Boolean vendedorActivo = usuarioClient.verificarUsuarioActivo(dto.getVendedorId());
        if (vendedorActivo == null || !vendedorActivo) {
            throw new RuntimeException("La cuenta del vendedor no se encuentra activa o registrada en el sistema central");
        }

        Publicacion publicacion = new Publicacion();
        publicacion.setVendedorId(dto.getVendedorId());
        publicacion.setTitulo(dto.getTitulo());
        publicacion.setDescripcion(dto.getDescripcion());
        publicacion.setPrecio(dto.getPrecio());
        publicacion.setEstadoProducto(dto.getEstadoProducto());
        publicacion.setCategoria(dto.getCategoria());
        publicacion.setDisponible(true);

        return mapToDTO(publicacionRepository.save(publicacion));
    }

    public Optional<PublicacionResponseDTO> actualizar(Long id, PublicacionRequestDTO dto) {
        return publicacionRepository.findById(id).map(existente -> {
            existente.setTitulo(dto.getTitulo());
            existente.setDescripcion(dto.getDescripcion());
            existente.setPrecio(dto.getPrecio());
            existente.setEstadoProducto(dto.getEstadoProducto());
            existente.setCategoria(dto.getCategoria());
            return mapToDTO(publicacionRepository.save(existente));
        });
    }

    public void desactivarPublicacion(Long id) {
        publicacionRepository.findById(id).ifPresent(publicacion -> {
            publicacion.setDisponible(false);
            publicacionRepository.save(publicacion);
        });
    }
}