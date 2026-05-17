package cl.dsy1103.ms_reportes.repository;

import cl.dsy1103.ms_reportes.model.EstadoReporte;
import cl.dsy1103.ms_reportes.model.Reporte;
import cl.dsy1103.ms_reportes.model.TipoReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findByComunaIgnoreCaseAndEstado(String comuna, EstadoReporte estado);
    List<Reporte> findByTipoReporteAndEstado(TipoReporte tipoReporte, EstadoReporte estado);
    List<Reporte> findByEstado(EstadoReporte estado);
}