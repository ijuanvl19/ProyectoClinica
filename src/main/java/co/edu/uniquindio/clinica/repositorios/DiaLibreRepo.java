package co.edu.uniquindio.clinica.repositorios;

import co.edu.uniquindio.clinica.model.DiaLibre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaLibreRepo extends JpaRepository<DiaLibre, Integer> {
}
