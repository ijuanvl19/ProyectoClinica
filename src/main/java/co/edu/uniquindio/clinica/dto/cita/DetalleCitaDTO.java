package co.edu.uniquindio.clinica.dto.cita;

import co.edu.uniquindio.clinica.model.Cita;

import java.time.LocalDateTime;

public record DetalleCitaDTO(

    int codigoCita,
    String nombrepaciente,
    String nombremedico,
    LocalDateTime fecha,
    String motivo
) {
    public DetalleCitaDTO(Cita cita) {
        this(
                cita.getCodigo(),
                cita.getPaciente().getNombre(),
                cita.getMedico().getNombre(),
                cita.getFechaCita(),
                cita.getMotivo()
        );
    }
}

