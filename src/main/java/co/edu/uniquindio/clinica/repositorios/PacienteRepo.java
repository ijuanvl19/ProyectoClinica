package co.edu.uniquindio.clinica.repositorios;

import co.edu.uniquindio.clinica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepo extends JpaRepository<Paciente, Integer> {

    Paciente findByCorreo(String nombre);
    Paciente findByCedula(String cedula);
    @Query("""
            select p from Paciente p
            where p.codigo = :idPaciente
            and p.estado = co.edu.uniquindio.clinica.model.EstadoUsuario.ACTIVO
            """)
    Paciente findActivoById(Integer idPaciente);
}
