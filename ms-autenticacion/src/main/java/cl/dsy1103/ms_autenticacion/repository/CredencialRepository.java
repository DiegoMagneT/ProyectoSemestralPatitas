package cl.dsy1103.ms_autenticacion.repository;

import cl.dsy1103.ms_autenticacion.model.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CredencialRepository extends JpaRepository<Credencial, Long> {
    Optional<Credencial> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioId(Long usuarioId);
}