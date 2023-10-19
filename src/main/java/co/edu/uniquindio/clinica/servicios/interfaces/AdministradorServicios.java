package co.edu.uniquindio.clinica.servicios.interfaces;

import co.edu.uniquindio.clinica.dto.pqrs.DetallePQRSDTO;
import co.edu.uniquindio.clinica.dto.pqrs.ItemPQRSDTO;
import co.edu.uniquindio.clinica.dto.RegistroRespuestaDTO;
import co.edu.uniquindio.clinica.dto.admin.*;
import co.edu.uniquindio.clinica.dto.cita.ItemCitaDTO;
import co.edu.uniquindio.clinica.model.EstadoPQRS;

import java.util.List;

public interface AdministradorServicios {
    //admin puede gestionar médicos
    int crearMedico(RegistroMedicoDTO medico);
    String acualizarMedico(DetalleMedicoDTO medico) throws Exception;
    String eliminarMedico(int codigo) throws Exception;
    List<ItemMedicoDto> listarMedicos() throws Exception;
    List<ItemPQRSDTO>ListarPQRS() throws Exception;
    DetallePQRSDTO verDetellaesPQRS(int codigo) throws Exception;
    List<ItemCitaDTO>listarCitas()throws Exception;
    int responderPQRS(RegistroRespuestaDTO registroRespuestaDTO) throws Exception;
    void cambiarEstadoPQRS(int codigoPQRS, EstadoPQRS estadoPQRS) throws Exception;


    //para poder atualizar el medico
    DetalleMedicoDTO obtenerMedico(int codigo) throws Exception;
}
