package co.edu.uniquindio.clinica.repositorios;

import co.edu.uniquindio.clinica.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Administrador, Integer> {
    Optional<Administrador> findByCorreo(String email);
}
