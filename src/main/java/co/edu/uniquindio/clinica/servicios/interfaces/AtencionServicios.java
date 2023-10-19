package co.edu.uniquindio.clinica.servicios.interfaces;

import co.edu.uniquindio.clinica.dto.cita.DetalleAtencionMedicaDTO;
import co.edu.uniquindio.clinica.dto.medico.RegistroAtencionDTO;

import java.util.List;

public interface AtencionServicios {

     int crear(RegistroAtencionDTO registroAtencionDTO);
     List<DetalleAtencionMedicaDTO> listar();
     DetalleAtencionMedicaDTO update(DetalleAtencionMedicaDTO datos);
     String eliminar(int codigo);
     DetalleAtencionMedicaDTO obtener(int codigo);
}
