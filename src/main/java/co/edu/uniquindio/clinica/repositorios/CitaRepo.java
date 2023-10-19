package co.edu.uniquindio.clinica.repositorios;

import co.edu.uniquindio.clinica.model.Cita;
import co.edu.uniquindio.clinica.model.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepo extends JpaRepository<Cita, Integer> {
    List<Cita> findAllByPacienteCodigo(int codigoPaciente);
    List<Cita> findCitaByPacienteCodigoAndFechaCitaBetween(int codigoPaciente, LocalDateTime fechaCita, LocalDateTime fechaCita2);
    List<Cita> findByMedicoCodigo(int codigoMedico);
    Boolean existsByPacienteCodigoAndFechaCitaBetween(
            Integer idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);
    Boolean existsByMedicoCodigoAndFechaCita(
            Integer idMedico, LocalDateTime fecha
    );
    Integer countByPacienteCodigoAndEstado(Integer idPaciente, EstadoCita estado);

    List<Cita> findByEstadoAndMedicoCodigo(EstadoCita estado, int codigoMedico);
    // relación entre Cita y Medico a través de Atencion
    List<Cita> findAllByAtencionCitaMedicoCodigo(int codigoMedico);

}
