package co.edu.uniquindio.clinica.servicios.interfaces;

import co.edu.uniquindio.clinica.dto.cita.DetalleAtencionMedicaDTO;
import co.edu.uniquindio.clinica.dto.admin.DetalleMedicoDTO;
import co.edu.uniquindio.clinica.dto.cita.ItemCitaDTO;
import co.edu.uniquindio.clinica.dto.admin.ItemMedicoDto;
import co.edu.uniquindio.clinica.dto.admin.RegistroMedicoDTO;
import co.edu.uniquindio.clinica.dto.medico.*;

import java.util.List;

public interface MedicoServicios {
    int crearMedico(RegistroMedicoDTO medicoDTO);
    String acualizarMedico(DetalleMedicoDTO medicoDTO) throws Exception;
    String eliminarMedico(int codigo) throws Exception;
    DetalleMedicoDTO obtenerMedico(int codigo) throws Exception;
    List<ItemMedicoDto> listarMedicos() throws Exception;
    List<ItemCitaDTO> listarCitasPendientes(int codigoMedico);
    int atenderCita(RegistroAtencionDTO registroAtencionDTO) throws Exception;
    List<ItemCitaDTO> listarCitasPaciente(int codigoPaciente) throws Exception;//historial medico
    int agendarDiaLibre(DiaLibreDTO diaLibreDTO) throws Exception;
    List<ItemCitaDTO> listarCitasRealizadasMedico(int codigoMedico) throws Exception; // listado de consultas
    DetalleAtencionMedicaDTO verDetalleAtencion(int codigoCita) throws Exception;


}
