package cl.dsy1103.ms_refugios.repository;

import cl.dsy1103.ms_refugios.model.Refugio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RefugioRepository extends JpaRepository<Refugio, Long> {

    List<Refugio> findByCiudadIgnoreCaseAndActivoTrue(String ciudad);
    List<Refugio> findByActivoTrue();
}
