package co.edu.uniquindio.clinica.dto.cita;

import co.edu.uniquindio.clinica.model.Especialidad;
import co.edu.uniquindio.clinica.model.EstadoCita;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendarCitaDTO(
        @NotNull  Integer idPaciente,
        Integer idMedico,
        LocalDateTime fechaCreacion,
        @NotNull @Future LocalDateTime fecha,
        String motivo,
        Especialidad especialidad
        ) {
}
