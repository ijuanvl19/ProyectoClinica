package co.edu.uniquindio.clinica.dto.admin;
import co.edu.uniquindio.clinica.model.*;
import co.edu.uniquindio.clinica.model.Especialidad;

public record ItemMedicoDto(
        int codigo,
        String nombre,
        String cedula,
        String urlFoto,
        Especialidad especialidad
) {
    public ItemMedicoDto(Medico medico) {
        this(
                medico.getCodigo(),
                medico.getNombre(),
                medico.getCedula(),
                medico.getUrlFoto(),
                medico.getEspecialidad()
        );
    }
}
