package co.edu.uniquindio.clinica.servicios.interfaces;

import co.edu.uniquindio.clinica.dto.cita.AgendarCitaDTO;
import co.edu.uniquindio.clinica.dto.cita.CancelamientoCitaDTO;
import co.edu.uniquindio.clinica.dto.cita.DetalleAtencionMedicaDTO;
import co.edu.uniquindio.clinica.dto.cita.ItemCitaDTO;
import co.edu.uniquindio.clinica.dto.cita.DetalleCitaDTO;
import co.edu.uniquindio.clinica.dto.paciente.FiltroBusquedaDTO;

import java.util.List;

public interface CitaServicios {
    //validar: Un paciente no puede tener más de tres citas agendadas al mismo tiempo.
    //Un paciente puede agendar citas
    DetalleCitaDTO agendarCita(AgendarCitaDTO datos);
    //requisito adicional
    void cancelarCita(CancelamientoCitaDTO datos);
    public List<ItemCitaDTO> listarCitas();
    //En todo momento, el paciente podrá ver todo el historial de atenciones que ha tenido
    //citas que ya han pasado, tienen atención o no si la cita se ha cancelado
    List<ItemCitaDTO> listarHistorialPaciente(int codigoPaciente);
    //citas pendientes
    List<ItemCitaDTO> listarCitasPendientesPaciente(int codigoPaciente);
    /*El paciente podrá ver todos los detalles de cada consulta,
    incluyendo notas del médico, diagnóstico y tratamiento.
     */
    DetalleAtencionMedicaDTO verDetalleCita(int codigoCita);
    //filtrar las citas por med y fecha
    List<ItemCitaDTO> filtrarCitasPorMedico(int idMedico);
    List<ItemCitaDTO> filtrarCitasPorFecha(FiltroBusquedaDTO filtroBusquedaDTO);

    //medico
    //Al ingreso, el médico podrá ver las citas que tiene pendientes por ser atendidas.
    void filtrarCitaspendientesMedico();
    //podrá atender citas del dia actual, validar que solo puede atender citas del dia actual
    //param diagnostico, tratamiento, notas medicas, llegan desde el cliente(formulario)
    void atenderCita();
    //todas las consultas que ha realizado un medico
    //param
    void listarTodasCitasMedico(int codigoMedico);

    //admin


}
