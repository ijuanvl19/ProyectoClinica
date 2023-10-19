package co.edu.uniquindio.clinica.dto.cita;

import co.edu.uniquindio.clinica.model.*;
import co.edu.uniquindio.clinica.model.Especialidad;
import co.edu.uniquindio.clinica.model.EstadoCita;

import java.time.LocalDateTime;

public record ItemCitaDTO(
        int codigo,
        String cedulaPaciente,
        String nombrePaciente,
        String nombreMedico,
        Especialidad especialidad,
        EstadoCita estadoCita,
        LocalDateTime fecha
) {
    public ItemCitaDTO(Cita c){
        this(
                c.getCodigo(),
                c.getPaciente().getCedula(),
                c.getPaciente().getNombre(),
                c.getMedico().getNombre(),
                c.getMedico().getEspecialidad(),
                c.getEstado(),
                c.getFechaCita()
        );
    }
}
