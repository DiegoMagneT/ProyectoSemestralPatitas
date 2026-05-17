package cl.dsy1103.ms_mascotas.repository;

import cl.dsy1103.ms_mascotas.model.Especie;
import cl.dsy1103.ms_mascotas.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByRefugioId(Long refugioId);
    List<Mascota> findByEspecie(Especie especie);
}