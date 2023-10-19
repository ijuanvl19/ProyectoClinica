package co.edu.uniquindio.clinica.dto.admin;

import co.edu.uniquindio.clinica.model.HorarioMedico;

import java.time.LocalTime;

public record HorarioDTO(
        String dia,
        LocalTime horaInicio,
        LocalTime horaSalida

        ) {

        public HorarioDTO(HorarioMedico h){
                this(
                        h.getDia(),
                        h.getHoraInicio(),
                        h.getHoraFin()
                );
        }
}
