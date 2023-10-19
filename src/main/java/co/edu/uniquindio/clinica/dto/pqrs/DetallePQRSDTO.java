package co.edu.uniquindio.clinica.dto.pqrs;

import co.edu.uniquindio.clinica.dto.RespuestaDTO;
import co.edu.uniquindio.clinica.model.Especialidad;
import co.edu.uniquindio.clinica.model.EstadoPQRS;
import co.edu.uniquindio.clinica.model.Pqrs;

import java.time.LocalDateTime;
import java.util.List;

//TODOS los datos de un médico que se requiere ver en el sistema
public record DetallePQRSDTO(
        int codigo,
        EstadoPQRS estado,
        String motivo,
        String nombrePaciente,
        String nombreMedico,
        Especialidad especialidad,
        LocalDateTime fecha,
        List<RespuestaDTO> mensajes) {
    public DetallePQRSDTO(Pqrs pqrs, List<RespuestaDTO> mensajes){
        this(
                pqrs.getCodigo(),
                pqrs.getEstado(),
                pqrs.getMotivo(),
                pqrs.getCita().getPaciente().getNombre(),
                pqrs.getCita().getMedico().getNombre(),
                pqrs.getCita().getMedico().getEspecialidad(),
                pqrs.getFechaCreacion(),
                mensajes
        );
    }
}
