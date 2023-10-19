package co.edu.uniquindio.clinica.dto.pqrs;

import co.edu.uniquindio.clinica.model.EstadoPQRS;

import java.time.LocalDateTime;

public record RegistroPQRDTO(
        int codigoCita,
        String motivo,
        int codigoPaciente,
        String tipoPQR,
        EstadoPQRS estado,
        LocalDateTime fechaCreacion
) {
}
