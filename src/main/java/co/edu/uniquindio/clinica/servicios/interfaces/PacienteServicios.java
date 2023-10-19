package co.edu.uniquindio.clinica.servicios.interfaces;

import co.edu.uniquindio.clinica.dto.*;
import co.edu.uniquindio.clinica.dto.cita.AgendarCitaDTO;
import co.edu.uniquindio.clinica.dto.cita.DetalleAtencionMedicaDTO;
import co.edu.uniquindio.clinica.dto.cita.ItemCitaDTO;
import co.edu.uniquindio.clinica.dto.paciente.*;
import co.edu.uniquindio.clinica.dto.pqrs.DetallePQRSDTO;
import co.edu.uniquindio.clinica.dto.pqrs.ItemPQRSDTO;
import co.edu.uniquindio.clinica.dto.pqrs.RegistroPQRDTO;

import java.util.List;

public interface PacienteServicios {
    //servicio de registro

    //Un paciente puede registrarse
    int registrarse(RegistroPacienteDTO registroPacienteDTO);

    //Un paciente ´puede editar su info personal
    PacienteRespuestaDTO editarPerfil(DetallePacienteDTO datos);

    List<ItemPacienteDTO > listarTodos();

    //Un paciente ´puede recuperar su contraseña
    void recuperarPasswd();

    void cambiarPasswd(NuevaPasswordDTO nuevaPasswordDTO)throws Exception;

    void enviarLinkRecuperacion(String email)throws Exception;

    //Un paciente ´puede eliminar la cuenta
    String eliminarCuenta(int codigoPaciente)throws Exception;

    DetallePacienteDTO verDetallePaciente(int codigo) throws Exception;




    //servicios de cita
    void agendarCita(AgendarCitaDTO agendarCitaDTO) throws Exception;

    List<ItemCitaDTO> listarCitasPaciente(int codigoPaciente) throws Exception;

    List<ItemCitaDTO> filtrarCitasPorFecha(FiltroBusquedaDTO filtroBusquedaDTO) throws Exception;

    List<ItemCitaDTO> filtrarCitasPorMedico(int idMedico);

    DetalleAtencionMedicaDTO verDetalleCita(int codigoCita) throws Exception;



    //servicios de PQRS
    void crearPQRS(RegistroPQRDTO registroPQRDTO) throws Exception;
    //en ttodo momento el paciente podrá ver el historial de las peticiones
    List<ItemPQRSDTO> listarPQRSPaciente(int codigoPaciente) throws Exception;

    int responderPQRS(RegistroRespuestaDTO registroRespuestaDTO) throws Exception;
    DetallePQRSDTO verDetallePQR(int codigo) throws Exception;





}
