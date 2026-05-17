package cl.dsy1103.ms_refugios.service;

import cl.dsy1103.ms_refugios.DTO.RefugioRequestDTO;
import cl.dsy1103.ms_refugios.DTO.RefugioResponseDTO;
import cl.dsy1103.ms_refugios.model.Refugio;
import cl.dsy1103.ms_refugios.repository.RefugioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefugioService {

    private final RefugioRepository refugioRepository;

    private RefugioResponseDTO mapToDTO(Refugio refugio) {
        return new RefugioResponseDTO(
                refugio.getId(),
                refugio.getNombre(),
                refugio.getDireccion(),
                refugio.getCiudad(),
                refugio.getCapacidadMax(),
                refugio.getCapacidadActual(),
                refugio.getOperadorId(),
                refugio.getActivo()
        );
    }

    public List<RefugioResponseDTO> obtenerTodosActivos() {
        return refugioRepository.findByActivoTrue()
                .stream()
                .map(refugio -> mapToDTO(refugio))
                .collect(Collectors.toList());
    }

    public Optional<RefugioResponseDTO> obtenerPorId(Long id) {
        return refugioRepository.findById(id).map(refugio -> mapToDTO(refugio));
    }

    public List<RefugioResponseDTO> filtrarPorCiudad(String ciudad) {
        return refugioRepository.findByCiudadIgnoreCaseAndActivoTrue(ciudad)
                .stream()
                .map(refugio -> mapToDTO(refugio))
                .collect(Collectors.toList());
    }

    public RefugioResponseDTO guardar(RefugioRequestDTO dto) {
        Refugio refugio = new Refugio();
        refugio.setNombre(dto.getNombre());
        refugio.setDireccion(dto.getDireccion());
        refugio.setCiudad(dto.getCiudad());
        refugio.setCapacidadMax(dto.getCapacidadMax());
        refugio.setCapacidadActual(0); // Siempre inicia en 0
        refugio.setOperadorId(dto.getOperadorId());
        refugio.setActivo(true);

        return mapToDTO(refugioRepository.save(refugio));
    }

    public Optional<RefugioResponseDTO> actualizar(Long id, RefugioRequestDTO dto) {
        return refugioRepository.findById(id).map(existente -> {
            if (dto.getCapacidadMax() < existente.getCapacidadActual()) {
                throw new RuntimeException("La capacidad máxima no puede ser menor a los animales actualmente albergados");
            }
            existente.setNombre(dto.getNombre());
            existente.setDireccion(dto.getDireccion());
            existente.setCiudad(dto.getCiudad());
            existente.setCapacidadMax(dto.getCapacidadMax());
            return mapToDTO(refugioRepository.save(existente));
        });
    }

    public void eliminar(Long id) {
        refugioRepository.findById(id).ifPresent(refugio -> {
            refugio.setActivo(false);
            refugioRepository.save(refugio);
        });
    }

    public Integer consultarCapacidadDisponible(Long id) {
        return refugioRepository.findById(id)
                .map(refugio -> refugio.getCapacidadMax() - refugio.getCapacidadActual())
                .orElseThrow(() -> new RuntimeException("Refugio no encontrado"));
    }
}
