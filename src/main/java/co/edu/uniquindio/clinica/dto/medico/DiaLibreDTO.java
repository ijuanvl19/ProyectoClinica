package co.edu.uniquindio.clinica.dto.medico;

import co.edu.uniquindio.clinica.model.DiaLibre;

import java.time.LocalDate;

public record DiaLibreDTO(
        int codigo,
        int codigoMedico,
        LocalDate fecha
) {
    public DiaLibreDTO(DiaLibre diaLibre) {
        this(
                diaLibre.getCodigo(),
                diaLibre.getMedico().getCodigo(),
                diaLibre.getDia()
        );
    }
}
