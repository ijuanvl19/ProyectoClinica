package co.edu.uniquindio.clinica.repositorios;

import co.edu.uniquindio.clinica.model.Pqrs;
import co.edu.uniquindio.clinica.model.EstadoPQRS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PQRSRepo extends JpaRepository<Pqrs, Integer> {
    List<Pqrs> findAllByCita_PacienteCodigo(int codigoPaciente);
    Integer countByCita_PacienteCodigoAndEstadoAndEstado(Integer idPaciente, EstadoPQRS estado1, EstadoPQRS estado2);
    //List<Mensaje> findAllByMensajesCuentaCodigo(int codigoPaciente);
}
