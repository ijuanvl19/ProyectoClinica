package co.edu.uniquindio.clinica.dto.security;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class MensajeDTO<T> {
    private boolean error;
    private T respuesta;

}
