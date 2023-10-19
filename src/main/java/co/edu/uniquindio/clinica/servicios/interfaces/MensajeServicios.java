package co.edu.uniquindio.clinica.servicios.interfaces;

import co.edu.uniquindio.clinica.dto.RegistroRespuestaDTO;
import co.edu.uniquindio.clinica.dto.RespuestaDTO;

import java.util.List;

public interface MensajeServicios {
    int crear(RegistroRespuestaDTO mensajeDTO);
    List<RespuestaDTO> listar();
    RespuestaDTO update(String contenido);
    int eliminar(int codigo);
    RespuestaDTO obtener(int codigo);
}
