package cl.dsy1103.ms_mascotas.service;

import cl.dsy1103.ms_mascotas.client.RefugioClient;
import cl.dsy1103.ms_mascotas.DTO.MascotaRequestDTO;
import cl.dsy1103.ms_mascotas.DTO.MascotaResponseDTO;
import cl.dsy1103.ms_mascotas.model.Especie;
import cl.dsy1103.ms_mascotas.model.EstadoMascota;
import cl.dsy1103.ms_mascotas.model.Mascota;
import cl.dsy1103.ms_mascotas.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final RefugioClient refugioClient;

    private MascotaResponseDTO mapToDTO(Mascota mascota) {
        return new MascotaResponseDTO(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getEspecie().name(),
                mascota.getRaza(),
                mascota.getEdadMeses(),
                mascota.getDescripcion(),
                mascota.getEstado().name(),
                mascota.getRefugioId()
        );
    }

    public List<MascotaResponseDTO> obtenerTodas() {
        return mascotaRepository.findAll()
                .stream()
                .map(mascota -> mapToDTO(mascota))
                .collect(Collectors.toList());
    }

    public Optional<MascotaResponseDTO> obtenerPorId(Long id) {
        return mascotaRepository.findById(id).map(mascota -> mapToDTO(mascota));
    }

    public List<MascotaResponseDTO> obtenerPorRefugio(Long refugioId) {
        return mascotaRepository.findByRefugioId(refugioId)
                .stream()
                .map(mascota -> mapToDTO(mascota))
                .collect(Collectors.toList());
    }

    public List<MascotaResponseDTO> obtenerPorEspecie(Especie especie) {
        return mascotaRepository.findByEspecie(especie)
                .stream()
                .map(mascota -> mapToDTO(mascota))
                .collect(Collectors.toList());
    }

    public MascotaResponseDTO guardar(MascotaRequestDTO dto) {

        Integer disponible = refugioClient.obtenerCapacidadDisponible(dto.getRefugioId());

        if (disponible == null || disponible <= 0) {
            throw new RuntimeException("El refugio asignado no cuenta con capacidad disponible para registrar nuevos animales");
        }

        Mascota mascota = new Mascota();
        mascota.setNombre(dto.getNombre());
        mascota.setEspecie(dto.getEspecie());
        mascota.setRaza(dto.getRaza());
        mascota.setEdadMeses(dto.getEdadMeses());
        mascota.setDescripcion(dto.getDescripcion());
        mascota.setEstado(dto.getEstado());
        mascota.setRefugioId(dto.getRefugioId());

        return mapToDTO(mascotaRepository.save(mascota));
    }

    public Optional<MascotaResponseDTO> actualizar(Long id, MascotaRequestDTO dto) {
        return mascotaRepository.findById(id).map(existente -> {
            existente.setNombre(dto.getNombre());
            existente.setEspecie(dto.getEspecie());
            existente.setRaza(dto.getRaza());
            existente.setEdadMeses(dto.getEdadMeses());
            existente.setDescripcion(dto.getDescripcion());
            existente.setRefugioId(dto.getRefugioId());
            return mapToDTO(mascotaRepository.save(existente));
        });
    }

    public Optional<MascotaResponseDTO> cambiarEstado(Long id, EstadoMascota nuevoEstado) {
        return mascotaRepository.findById(id).map(mascota -> {
            mascota.setEstado(nuevoEstado);
            return mapToDTO(mascotaRepository.save(mascota));
        });
    }
}