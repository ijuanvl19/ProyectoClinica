package co.edu.uniquindio.clinica.dto.cita;

import co.edu.uniquindio.clinica.model.MotivoCancelamiento;

public record CancelamientoCitaDTO(
        Integer idCita,
        MotivoCancelamiento motivo
) {
}
