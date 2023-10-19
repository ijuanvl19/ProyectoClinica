package co.edu.uniquindio.clinica.dto.pqrs;

import co.edu.uniquindio.clinica.model.*;
import co.edu.uniquindio.clinica.model.EstadoPQRS;

import java.time.LocalDateTime;

public record ItemPQRSDTO(
        int codigo,
        EstadoPQRS estado,
        //no es necesario ver el motivo en un overview
        LocalDateTime fecha,
        String nombrePaciente

) {
    public ItemPQRSDTO(Pqrs p) {
        this(
                p.getCodigo(),
                p.getEstado(),
                p.getFechaCreacion(),
                p.getCita().getPaciente().getNombre()
        );
    }
}
