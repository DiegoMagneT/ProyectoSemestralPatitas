package cl.dsy1103.ms_reportes.service;

import cl.dsy1103.ms_reportes.client.UsuarioClient;
import cl.dsy1103.ms_reportes.DTO.ReporteRequestDTO;
import cl.dsy1103.ms_reportes.DTO.ReporteResponseDTO;
import cl.dsy1103.ms_reportes.model.EstadoReporte;
import cl.dsy1103.ms_reportes.model.Reporte;
import cl.dsy1103.ms_reportes.model.TipoReporte;
import cl.dsy1103.ms_reportes.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final UsuarioClient usuarioClient;

    private ReporteResponseDTO mapToDTO(Reporte reporte) {
        return new ReporteResponseDTO(
                reporte.getId(),
                reporte.getUsuarioId(),
                reporte.getTitulo(),
                reporte.getTipoReporte().name(),
                reporte.getComuna(),
                reporte.getDescripcion(),
                reporte.getContactoEmergencia(),
                reporte.getEstado().name(),
                reporte.getFechaReporte()
        );
    }

    public List<ReporteResponseDTO> obtenerActivos() {
        return reporteRepository.findByEstado(EstadoReporte.ACTIVO)
                .stream()
                .map(reporte -> mapToDTO(reporte))
                .collect(Collectors.toList());
    }

    public Optional<ReporteResponseDTO> obtenerPorId(Long id) {
        return reporteRepository.findById(id).map(reporte -> mapToDTO(reporte));
    }

    public List<ReporteResponseDTO> filtrarPorComuna(String comuna) {
        return reporteRepository.findByComunaIgnoreCaseAndEstado(comuna, EstadoReporte.ACTIVO)
                .stream()
                .map(reporte -> mapToDTO(reporte))
                .collect(Collectors.toList());
    }

    public List<ReporteResponseDTO> filtrarPorTipo(TipoReporte tipo) {
        return reporteRepository.findByTipoReporteAndEstado(tipo, EstadoReporte.ACTIVO)
                .stream()
                .map(reporte -> mapToDTO(reporte))
                .collect(Collectors.toList());
    }

    public ReporteResponseDTO guardar(ReporteRequestDTO dto) {
        // Validación remota mediante WebClient
        Boolean usuarioValido = usuarioClient.verificarUsuarioActivo(dto.getUsuarioId());
        if (usuarioValido == null || !usuarioValido) {
            throw new RuntimeException("No se puede generar el reporte debido a que el usuario no tiene una cuenta activa");
        }

        Reporte reporte = new Reporte();
        reporte.setUsuarioId(dto.getUsuarioId());
        reporte.setTitulo(dto.getTitulo());
        reporte.setTipoReporte(dto.getTipoReporte());
        reporte.setComuna(dto.getComuna());
        reporte.setDescripcion(dto.getDescripcion());
        reporte.setContactoEmergencia(dto.getContactoEmergencia());
        reporte.setEstado(EstadoReporte.ACTIVO);

        return mapToDTO(reporteRepository.save(reporte));
    }

    public Optional<ReporteResponseDTO> resolverReporte(Long id) {
        return reporteRepository.findById(id).map(reporte -> {
            reporte.setEstado(EstadoReporte.RESUELTO);
            return mapToDTO(reporteRepository.save(reporte));
        });
    }
}