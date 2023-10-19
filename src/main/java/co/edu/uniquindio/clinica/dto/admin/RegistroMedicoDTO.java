package co.edu.uniquindio.clinica.dto.admin;

import co.edu.uniquindio.clinica.model.Ciudad;
import co.edu.uniquindio.clinica.model.Especialidad;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record RegistroMedicoDTO(
        @NotBlank @Length(max = 200) String nombre,
        @NotBlank @Length(max = 10) String cedula,
        @NotNull Ciudad ciudad,
        @NotNull Especialidad especialidad,
        @NotBlank @Length(max = 20) String telefono,
        @NotBlank @Email @Length(max = 80) String correo,
        @NotBlank String password,
        @NotBlank String urlFoto,
        @NotEmpty List<HorarioDTO> horarios
) {
}
