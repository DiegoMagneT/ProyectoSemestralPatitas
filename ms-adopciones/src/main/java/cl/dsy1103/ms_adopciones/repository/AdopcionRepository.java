package cl.dsy1103.ms_adopciones.repository;

import cl.dsy1103.ms_adopciones.model.Adopcion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdopcionRepository extends JpaRepository<Adopcion, Long> {
    List<Adopcion> findBySolicitanteId(Long solicitanteId);
}