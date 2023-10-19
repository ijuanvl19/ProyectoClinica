package co.edu.uniquindio.clinica.servicios.impl;

import co.edu.uniquindio.clinica.dto.cita.DetalleAtencionMedicaDTO;
import co.edu.uniquindio.clinica.dto.admin.*;
import co.edu.uniquindio.clinica.dto.cita.ItemCitaDTO;
import co.edu.uniquindio.clinica.dto.medico.DiaLibreDTO;
import co.edu.uniquindio.clinica.dto.medico.RegistroAtencionDTO;
import co.edu.uniquindio.clinica.model.Cita;
import co.edu.uniquindio.clinica.model.HorarioMedico;
import co.edu.uniquindio.clinica.model.Medico;
import co.edu.uniquindio.clinica.model.EstadoCita;
import co.edu.uniquindio.clinica.model.EstadoUsuario;
import co.edu.uniquindio.clinica.repositorios.CitaRepo;
import co.edu.uniquindio.clinica.repositorios.HorarioRepo;
import co.edu.uniquindio.clinica.repositorios.MedicoRepo;
import co.edu.uniquindio.clinica.servicios.interfaces.MedicoServicios;
import co.edu.uniquindio.clinica.servicios.validaciones.registros.ValidacionDeDuplicados;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MedicoServicioImpl implements MedicoServicios {
    private final CitaRepo citaRepo;
    private final AtencionServicioImpl atencionServicio;
    private final PacienteServicioImpl pacienteServicio;
    private final DiaLibreServiciosImpl diaLibreServicios;
    private final MedicoRepo medicoRepo;
    private final ValidacionDeDuplicados validacion;
    private final HorarioRepo horarioRepo;

    @Override
    public int crearMedico(RegistroMedicoDTO medicoDTO) {
        validacion.validarMedico(medicoDTO);
        Medico medico = medicoRepo.save(new Medico(medicoDTO));
        asignarHorariosMedico(medico, medicoDTO.horarios());
        System.out.println("Medico " + medico.getCodigo() + " creado");
        return medico.getCodigo();
    }
    private void asignarHorariosMedico(Medico medicoNuevo, List<HorarioDTO> horarios) {
        horarios.forEach(h -> {
            HorarioMedico hm = new HorarioMedico(h,medicoNuevo);
            horarioRepo.save(hm);
        });
    }
    @Override
    public String acualizarMedico(DetalleMedicoDTO medicoDTO) throws Exception {
        Medico medico = validar(medicoDTO.codigo());
        medico.actualizar(medicoDTO);
        //Como el objeto paciente ya tiene un id, el save() no crea un nuevo registro sino que actualiza el que ya existe
        medicoRepo.save(medico);//si es necesario?

        return "Médico " + medico.getCodigo() + " actualizado con éxito";
    }
    @Override
    public String eliminarMedico(int codigo) throws Exception {
        Medico medico = validar(codigo);
        medico.setEstado(EstadoUsuario.INACTIVO);
        medicoRepo.save(medico);//Si es necesario? ya quedó seteado es valor
        return "Médico eliminado con éxito";
    }

    @Override
    public List<ItemMedicoDto> listarMedicos() throws Exception {
        List<Medico> medicos = medicoRepo.findAll();
        if (medicos.isEmpty()) {
            throw new Exception("No hay médicos registrados");
        }
        return medicos.stream()
                .filter(m -> m.getEstado() == EstadoUsuario.ACTIVO)
                .map(ItemMedicoDto::new)
                .collect(Collectors.toList());//a partir de java 8
    }

    @Override
    public DetalleMedicoDTO obtenerMedico(int codigo)  {
        Medico medico = validar(codigo);
        if(medico.getEstado()==EstadoUsuario.INACTIVO){
            throw new ValidationException("El Medico esta Inactivo");
        }
        List<HorarioMedico> horarios = horarioRepo.findAllByMedicoCodigo(codigo);
        List<HorarioDTO> horariosDTO = horarios.stream()
                .map(HorarioDTO::new)
                .toList();
        return new DetalleMedicoDTO(medico, horariosDTO);
    }
    @Override
    public List<ItemCitaDTO> listarCitasPendientes(int codigoMedico) {
        List<Cita> citas = citaRepo.findByEstadoAndMedicoCodigo(EstadoCita.PROGRAMADA, codigoMedico);
        if (citas.isEmpty()) {
            throw new ValidationException("No hay citas pendientes");
        }
        return citas.stream().map(ItemCitaDTO::new).toList();
    }
    //cundo el Medico llene el formulario con sus notas médicas y presione enviar se invocará este meth
    @Override
    public int atenderCita(RegistroAtencionDTO registroAtencionDTO) throws Exception {
        return atencionServicio.crear(registroAtencionDTO);
    }

    @Override
    public List<ItemCitaDTO> listarCitasPaciente(int codigoPaciente) throws Exception {
        return pacienteServicio.listarCitasPaciente(codigoPaciente);
    }

    @Override
    public int agendarDiaLibre(DiaLibreDTO diaLibreDTO) throws Exception {
        return diaLibreServicios.crear(diaLibreDTO);
    }

    @Override
    public List<ItemCitaDTO> listarCitasRealizadasMedico(int codigoMedico) throws Exception {
        List<Cita> citas = citaRepo.findAllByAtencionCitaMedicoCodigo(codigoMedico);
        if(citas.isEmpty()){
            throw new ValidationException("No existen citas atendidas por el medico "+codigoMedico);
        }
        return citas.stream()
                .map(ItemCitaDTO::new)
                .toList();
    }

    @Override
    public DetalleAtencionMedicaDTO verDetalleAtencion(int codigoCita) throws Exception {
        return atencionServicio.obtener(codigoCita);
    }

    private Medico validar(int codigo) {
        Optional<Medico> opcional = medicoRepo.findById(codigo);
        if (opcional.isEmpty()) {
            throw new ValidationException("No existe un médico con el código " + codigo);
        }
        return opcional.get();
    }
}
