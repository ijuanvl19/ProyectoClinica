package co.edu.uniquindio.clinica.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank
        @Email
        String user,
        @NotBlank
        String passwd
) {
}
