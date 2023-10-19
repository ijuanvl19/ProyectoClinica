package co.edu.uniquindio.clinica.servicios.interfaces;

import co.edu.uniquindio.clinica.dto.EmailDTO;

public interface EmailServicios {
    void enviarEmail(EmailDTO emailDTO) throws Exception;
}
