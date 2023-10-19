package co.edu.uniquindio.clinica.servicios.impl;

import co.edu.uniquindio.clinica.dto.*;
import co.edu.uniquindio.clinica.dto.cita.AgendarCitaDTO;
import co.edu.uniquindio.clinica.dto.cita.DetalleAtencionMedicaDTO;
import co.edu.uniquindio.clinica.dto.cita.ItemCitaDTO;
import co.edu.uniquindio.clinica.dto.paciente.*;
import co.edu.uniquindio.clinica.dto.pqrs.DetallePQRSDTO;
import co.edu.uniquindio.clinica.dto.pqrs.ItemPQRSDTO;
import co.edu.uniquindio.clinica.dto.pqrs.RegistroPQRDTO;
import co.edu.uniquindio.clinica.infra.errors.ValidacionDeIntegridadE;
import co.edu.uniquindio.clinica.model.*;
import co.edu.uniquindio.clinica.repositorios.CitaRepo;
import co.edu.uniquindio.clinica.repositorios.PQRSRepo;
import co.edu.uniquindio.clinica.repositorios.PacienteRepo;
import co.edu.uniquindio.clinica.servicios.interfaces.PacienteServicios;
import co.edu.uniquindio.clinica.servicios.validaciones.registros.ValidacionDeDuplicados;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor//es como escribir el constr de esta clase, instanciando todos sus campos, también se puede usar @AllArgsConstructor
public class PacienteServicioImpl implements PacienteServicios {

    private final PacienteRepo pacienteRepo;
    private final ValidacionDeDuplicados validacion;
    private final CitaServiciosImpl citaServiciosImpl;
    private final CitaRepo citaRepo;
    private final PQRServicioImpl pqrServicio;
    private final PQRSRepo pqrsRepo;
    private  final PasswordEncoder passwordEncoder;

    @Override
    public int registrarse(RegistroPacienteDTO pacienteDTO){
        validacion.validarPaciente(pacienteDTO);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordEncriptada = encoder.encode( pacienteDTO.password() );
        Paciente paciente = pacienteRepo.save(new Paciente(pacienteDTO,passwordEncriptada));
        paciente.setPasswd(passwordEncoder.encode(pacienteDTO.password()));
        return paciente.getCodigo();
    }

    @Override
    @Transactional
    public PacienteRespuestaDTO editarPerfil(DetallePacienteDTO datos) {
        Optional<Paciente> opcional = pacienteRepo.findById(datos.codigo());
        if( opcional.isEmpty() ){
            throw new ValidacionDeIntegridadE("No existe un paciente con el código "+datos.codigo());
        }
        Paciente paciente = opcional.get();
        validacion.validarActualizacionPaciente(datos);
        paciente.actualizar(datos);
        return new PacienteRespuestaDTO(paciente);
    }

    @Override
    public List<ItemPacienteDTO> listarTodos() {
        List<Paciente> pacientes = pacienteRepo.findAll();
        if (pacientes.isEmpty()) {
            throw new ValidationException("No hay pacientes registrados");
        }
        //Hacemos un mapeo de cada uno de los objetos de tipo Paciente a objetos de tipo ItemPacienteDTO
        return pacientes.stream()
                .filter(p -> p.getEstado() == EstadoUsuario.ACTIVO)
                .map(ItemPacienteDTO::new)
                .toList();
    }

    @Override
    @Transactional
    public String eliminarCuenta(int codigo){
        Optional<Paciente> optional = pacienteRepo.findById(codigo);
        if( optional.isEmpty() ){
            throw new ValidacionDeIntegridadE("No existe un paciente con el código "+codigo);
        }
        Paciente paciente = optional.get();
        paciente.inactivar();
        return "Médico eliminado con éxito";
    }

    @Override
    public DetallePacienteDTO verDetallePaciente(int codigo)  {
        Optional<Paciente> optional = pacienteRepo.findById(codigo);
        if( optional.isEmpty() ){
            throw new ValidacionDeIntegridadE("No existe un paciente con el código "+codigo);
        }
        Paciente paciente = optional.get();
        if(paciente.getEstado()==EstadoUsuario.INACTIVO){
            throw new ValidationException("El paciente esta Inactivo");
        }
        //Hacemos un mapeo de un objeto de tipo Paciente a un objeto de tipo DetallePacienteDTO
        return new DetallePacienteDTO(paciente);
    }

    @Override
    public void recuperarPasswd() {
        //
    }

    @Override
    public void cambiarPasswd(NuevaPasswordDTO nuevaPasswordDTO) throws Exception {
        //
    }

    @Override
    public void enviarLinkRecuperacion(String email) throws Exception {
        //
    }

    @Override
    public void agendarCita(AgendarCitaDTO agendarCitaDTO) throws Exception {
        citaServiciosImpl.agendarCita(agendarCitaDTO);
    }
    @Override
    public List<ItemCitaDTO> listarCitasPaciente(int codigoPaciente) throws Exception {
        List<Cita> citas = citaRepo.findByEstadoAndPacienteCodigo(EstadoCita.PROGRAMADA, codigoPaciente);
        if(citas.isEmpty()){
            throw new ValidationException("No existen citas creadas para el paciente "+codigoPaciente);
        }
        return citas.stream()
                .map(ItemCitaDTO::new)
                .toList();
    }

    @Override
    public List<ItemCitaDTO> filtrarCitasPorFecha(FiltroBusquedaDTO filtroBusquedaDTO){
        int codigoPaciente = filtroBusquedaDTO.codigoPaciente();
        LocalDateTime fechaInicio = filtroBusquedaDTO.fechaInicio();
        LocalDateTime fechaFin = filtroBusquedaDTO.fechaFin();
        List<Cita> citas = citaRepo.findCitaByPacienteCodigoAndFechaCitaBetween(codigoPaciente, fechaInicio, fechaFin);
        if(citas.isEmpty()){
            throw new ValidationException("No existen citas en esa fecha");
        }
        return citas.stream().map(ItemCitaDTO::new).toList();
    }

    @Override
    public List<ItemCitaDTO> filtrarCitasPorMedico(int idMedico) {
        List<Cita> citas = citaRepo.findByMedicoCodigo(idMedico);
        if(citas.isEmpty()){
            throw new ValidationException("No existen citas asociandas al medico "+idMedico);
        }
        return citas.stream().map(ItemCitaDTO::new).toList();
    }

    @Override
    public DetalleAtencionMedicaDTO verDetalleCita(int codigoCita) throws Exception {
        return citaServiciosImpl.verDetalleCita(codigoCita);
    }

    @Override
    public void crearPQRS(RegistroPQRDTO registroPQRDTO) throws Exception {
        pqrServicio.crearPQRS(registroPQRDTO);
    }

    @Override
    public List<ItemPQRSDTO> listarPQRSPaciente(int codigoPaciente) {
        List<Pqrs> pqrs = pqrsRepo.findAllByCita_PacienteCodigo(codigoPaciente);
        if(pqrs.isEmpty()){
            throw new ValidationException("No existen PQRS creadas para el paciente "+codigoPaciente);
        }
        return pqrs.stream()
                .map(ItemPQRSDTO::new)
                .toList();
    }

    @Override
    public int responderPQRS(RegistroRespuestaDTO registroRespuestaDTO) throws Exception {
        return pqrServicio.responderPQRS(registroRespuestaDTO);
    }

    @Override
    public DetallePQRSDTO verDetallePQR(int codigo) throws Exception {
        return pqrServicio.verDetallePQRS(codigo);
    }
}
