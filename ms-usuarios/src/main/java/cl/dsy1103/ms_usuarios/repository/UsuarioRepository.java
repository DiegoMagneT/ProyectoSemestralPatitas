package cl.dsy1103.ms_usuarios.repository;

import cl.dsy1103.ms_usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);
}