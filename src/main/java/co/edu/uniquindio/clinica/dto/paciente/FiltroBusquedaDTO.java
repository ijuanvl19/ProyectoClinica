package co.edu.uniquindio.clinica.dto.paciente;

import java.time.LocalDateTime;

public record FiltroBusquedaDTO(
        int codigoPaciente,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin
) {
}
