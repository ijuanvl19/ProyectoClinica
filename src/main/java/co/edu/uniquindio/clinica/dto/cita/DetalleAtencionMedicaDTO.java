package co.edu.uniquindio.clinica.dto.cita;

import co.edu.uniquindio.clinica.model.Cita;
import co.edu.uniquindio.clinica.model.Especialidad;

import java.time.LocalDateTime;

public record DetalleAtencionMedicaDTO(
        int codigo,
        int codigoCita,
        String nombrePaciente,
        String nombreMedico,
        Especialidad especialidad,
        LocalDateTime fechaAtencion,
        String tratamiento,
        String notasMedicas,
        String diagnostico
) {
    public DetalleAtencionMedicaDTO(Cita cita){
        this(
                cita.getAtencion().getCodigo(),
                cita.getCodigo(),
                cita.getPaciente().getNombre(),
                cita.getMedico().getNombre(),
                cita.getMedico().getEspecialidad(),
                cita.getFechaCita(),
                cita.getAtencion().getTratamiento(),
                cita.getAtencion().getNotasMedicas(),
                cita.getAtencion().getDiagnostico()
        );
    }
}
