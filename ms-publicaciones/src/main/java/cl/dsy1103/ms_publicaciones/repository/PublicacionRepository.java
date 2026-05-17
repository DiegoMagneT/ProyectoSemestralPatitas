package cl.dsy1103.ms_publicaciones.repository;

import cl.dsy1103.ms_publicaciones.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    List<Publicacion> findByDisponibleTrue();
    List<Publicacion> findByCategoriaIgnoreCaseAndDisponibleTrue(String categoria);
    List<Publicacion> findByVendedorId(Long vendedorId);
}
