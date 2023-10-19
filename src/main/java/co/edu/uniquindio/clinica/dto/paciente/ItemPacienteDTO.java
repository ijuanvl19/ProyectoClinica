package co.edu.uniquindio.clinica.dto.paciente;

import co.edu.uniquindio.clinica.model.Paciente;
import co.edu.uniquindio.clinica.model.Ciudad;

public record ItemPacienteDTO(
        int codigo,
        String cedula,
        String nombre,
        Ciudad ciudad
) {
    public ItemPacienteDTO(Paciente paciente){
        this(
                paciente.getCodigo(),
                paciente.getCedula(),
                paciente.getNombre(),
                paciente.getCiudad()
        );
    }
}
