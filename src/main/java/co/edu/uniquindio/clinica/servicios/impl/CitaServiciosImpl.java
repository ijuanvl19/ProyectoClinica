package co.edu.uniquindio.clinica.servicios.impl;

import co.edu.uniquindio.clinica.dto.cita.CancelamientoCitaDTO;
import co.edu.uniquindio.clinica.dto.cita.DetalleAtencionMedicaDTO;
import co.edu.uniquindio.clinica.dto.cita.ItemCitaDTO;
import co.edu.uniquindio.clinica.dto.cita.DetalleCitaDTO;
import co.edu.uniquindio.clinica.infra.errors.ValidacionDeIntegridadE;
import co.edu.uniquindio.clinica.model.Atencion;
import co.edu.uniquindio.clinica.model.Cita;
import co.edu.uniquindio.clinica.model.Medico;
import co.edu.uniquindio.clinica.model.Paciente;
import co.edu.uniquindio.clinica.repositorios.AtencionRepo;
import co.edu.uniquindio.clinica.repositorios.CitaRepo;
import co.edu.uniquindio.clinica.repositorios.MedicoRepo;
import co.edu.uniquindio.clinica.repositorios.PacienteRepo;
import co.edu.uniquindio.clinica.dto.cita.AgendarCitaDTO;
import co.edu.uniquindio.clinica.servicios.interfaces.CitaServicios;
import co.edu.uniquindio.clinica.servicios.validaciones.agendar.ValidadorDeCitas;
import co.edu.uniquindio.clinica.servicios.validaciones.cancelar.ValidadorCancelamientoCitas;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class CitaServiciosImpl implements CitaServicios {
    private final CitaRepo citaRepo;
    private final PacienteRepo pacienteRepo;
    private final MedicoRepo medicoRepo;
    private final AtencionRepo atencionRepo;
    @Autowired
    List<ValidadorDeCitas> validadores;
    List<ValidadorCancelamientoCitas> validadoresCancelamiento;
    @Override
    public DetalleCitaDTO agendarCita(AgendarCitaDTO datos) {
        Optional<Paciente> opcional = pacienteRepo.findById(datos.idPaciente());
        if (opcional.isEmpty()){
                throw new ValidacionDeIntegridadE("Este id para el paciente no fue encontrado");
        }
        if (datos.idMedico()!=null && !medicoRepo.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridadE("Este id para el médico no fue encontrado");
        }
        //validaciones
        validadores.forEach(v->v.validar(datos));
        var paciente = opcional.get();
        var medico = seleccionarMedico(datos);
        if(medico==null){
            throw new ValidacionDeIntegridadE("No existen médicos disponibles para este " +
                    "horario y especialidad");
        }
        var cita = new Cita(LocalDateTime.now(), datos.fecha(), datos.motivo(), datos.estado(), medico, paciente);
        citaRepo.save(cita);
        return new DetalleCitaDTO(cita);
        /*toma de decisiones, comunicación, resolución de porblemas, coordinación*/
    }

    private Medico seleccionarMedico(AgendarCitaDTO datos) {

        if (datos.idMedico()!=null){
            return medicoRepo.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad()==null){
            throw new ValidacionDeIntegridadE("Debe seleccionar una especialidad para el médico");
        }
        return medicoRepo.seleccionaMedicoConEspecialidadEnFecha(datos.especialidad(),
                datos.fecha());
    }

    @Override
    public void cancelarCita(CancelamientoCitaDTO datos) {
        if (!citaRepo.existsById(datos.idCita())){
            throw new ValidacionDeIntegridadE("Id de la consulta do existe!");
        }
        validadoresCancelamiento.forEach(v->v.validar(datos));
        var cita = citaRepo.getReferenceById(datos.idCita());
        cita.cancelar(datos.motivo());
    }

    @Override
    public List<ItemCitaDTO> listarCitas() {
        List<Cita> citas = citaRepo.findAll();
        if(citas.isEmpty()){
            throw new ValidationException("No existen citas creadas");
        }
        return citas.stream()
                .map(ItemCitaDTO::new)
                .toList();
    }

    @Override
    public void listarHistorialPaciente(int codigoPaciente) {

    }

    @Override
    public void listarCitasPendientes(int codigoPaciente) {

    }

    @Override
    public DetalleAtencionMedicaDTO verDetalleCita(int codigoCita) {
        Optional<Cita> opcionalCita = citaRepo.findById(codigoCita);
        if( opcionalCita.isEmpty() ){
            throw new ValidacionDeIntegridadE("No existe una cita con el código "+codigoCita);
        }
        Cita cita = opcionalCita.get();
        Optional<Atencion> opcionalAtencion = atencionRepo.findAtencionByCitaCodigo(codigoCita);
        if(opcionalAtencion.isEmpty() ){
            throw new ValidacionDeIntegridadE("Aun no hay atención para la cita con código "+codigoCita);
        }
        return new DetalleAtencionMedicaDTO(cita);
    }

    @Override
    public void filtrarCitasPorMedico() {

    }

    @Override
    public void filtrarCitasPorFecha() {

    }

    @Override
    public void filtrarCitaspendientesMedico() {

    }

    @Override
    public void atenderCita() {

    }

    @Override
    public void listarTodasCitasMedico(int codigoMedico) {

    }
}
