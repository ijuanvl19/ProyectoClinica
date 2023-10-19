package co.edu.uniquindio.clinica.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class SesionDTO {
    @NotBlank
    @Email
    private String email;


    @NotBlank
    private String password;



}
