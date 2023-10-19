package co.edu.uniquindio.clinica.repositorios;

import co.edu.uniquindio.clinica.model.HorarioMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioMedicoRepo extends JpaRepository<HorarioMedico, Integer> {
}
