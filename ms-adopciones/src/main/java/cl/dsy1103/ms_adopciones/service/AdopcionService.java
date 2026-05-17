package cl.dsy1103.ms_adopciones.service;

import cl.dsy1103.ms_adopciones.DTO.AdopcionRequestDTO;
import cl.dsy1103.ms_adopciones.client.OrquestadorClient;
import cl.dsy1103.ms_adopciones.DTO.AdopcionResponseDTO;
import cl.dsy1103.ms_adopciones.model.Adopcion;
import cl.dsy1103.ms_adopciones.model.EstadoAdopcion;
import cl.dsy1103.ms_adopciones.repository.AdopcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdopcionService {

    private final AdopcionRepository adopcionRepository;
    private final OrquestadorClient orquestadorClient;

    private AdopcionResponseDTO mapToDTO(Adopcion adopcion) {
        return new AdopcionResponseDTO(
                adopcion.getId(),
                adopcion.getSolicitanteId(),
                adopcion.getMascotaId(),
                adopcion.getRefugioId(),
                adopcion.getEstado().name(),
                adopcion.getFechaSolicitud(),
                adopcion.getMotivoRechazo()
        );
    }

    public List<AdopcionResponseDTO> obtenerTodas() {
        return adopcionRepository.findAll()
                .stream()
                .map(adopcion -> mapToDTO(adopcion))
                .collect(Collectors.toList());
    }

    public Optional<AdopcionResponseDTO> obtenerPorId(Long id) {
        return adopcionRepository.findById(id).map(adopcion -> mapToDTO(adopcion));
    }

    public List<AdopcionResponseDTO> obtenerPorUsuario(Long usuarioId) {
        return adopcionRepository.findBySolicitanteId(usuarioId)
                .stream()
                .map(adopcion -> mapToDTO(adopcion))
                .collect(Collectors.toList());
    }

    @Transactional
    public AdopcionResponseDTO guardar(AdopcionRequestDTO dto) {
        Boolean solicitanteActivo = orquestadorClient.verificarUsuarioActivo(dto.getSolicitanteId());
        if (solicitanteActivo == null || !solicitanteActivo) {
            throw new RuntimeException("La cuenta de usuario del adoptante no se encuentra autorizada o activa");
        }

        Map<String, Object> fichaMascota = orquestadorClient.obtenerFichaMascota(dto.getMascotaId());
        if (fichaMascota == null || !fichaMascota.get("estado").equals("DISPONIBLE")) {
            throw new RuntimeException("La mascota seleccionada no se encuentra en estado DISPONIBLE para un nuevo proceso");
        }

        Integer cuposLibres = orquestadorClient.obtenerCapacidadDisponibleRefugio(dto.getRefugioId());
        if (cuposLibres == null || cuposLibres <= 0) {
            throw new RuntimeException("El refugio de origen no cumple con la capacidad operativa mínima requerida");
        }

        Adopcion adopcion = new Adopcion();
        adopcion.setSolicitanteId(dto.getSolicitanteId());
        adopcion.setMascotaId(dto.getMascotaId());
        adopcion.setRefugioId(dto.getRefugioId());
        adopcion.setEstado(EstadoAdopcion.PENDIENTE);

        orquestadorClient.actualizarEstadoMascotaRemoto(dto.getMascotaId(), "EN_PROCESO");

        return mapToDTO(adopcionRepository.save(adopcion));
    }

    @Transactional
    public Optional<AdopcionResponseDTO> aprobarSolicitud(Long id) {
        return adopcionRepository.findById(id).map(adopcion -> {
            if (adopcion.getEstado() != EstadoAdopcion.PENDIENTE) {
                throw new RuntimeException("La solicitud seleccionada ya ha sido procesada con anterioridad");
            }
            adopcion.setEstado(EstadoAdopcion.APROBADA);

            orquestadorClient.actualizarEstadoMascotaRemoto(adopcion.getMascotaId(), "ADOPTADO");

            return mapToDTO(adopcionRepository.save(adopcion));
        });
    }

    @Transactional
    public Optional<AdopcionResponseDTO> rechazarSolicitud(Long id, String motivo) {
        return adopcionRepository.findById(id).map(adopcion -> {
            if (adopcion.getEstado() != EstadoAdopcion.PENDIENTE) {
                throw new RuntimeException("La solicitud seleccionada ya ha sido procesada con anterioridad");
            }
            adopcion.setEstado(EstadoAdopcion.RECHAZADA);
            adopcion.setMotivoRechazo(motivo);

            orquestadorClient.actualizarEstadoMascotaRemoto(adopcion.getMascotaId(), "DISPONIBLE");

            return mapToDTO(adopcionRepository.save(adopcion));
        });
    }
}