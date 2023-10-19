package co.edu.uniquindio.clinica.dto;
import co.edu.uniquindio.clinica.model.Mensaje;

import java.time.LocalDateTime;

public record RespuestaDTO(
        int codigoMensaje,
        String contenido,
        String nombreUsuario,
        LocalDateTime fecha,
        String mensajesAsociados) {
    public RespuestaDTO(Mensaje mensaje){
        this(
                mensaje.getCodigo(),
                mensaje.getContenido(),
                mensaje.getCuenta().getCorreo(),
                mensaje.getFecha(),
                mensaje.getPqrs().getMotivo()
        );
    }

}
