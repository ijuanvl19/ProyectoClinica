package co.edu.uniquindio.clinica.servicios.interfaces;

import co.edu.uniquindio.clinica.infra.security.DatosJWTtoken;
import co.edu.uniquindio.clinica.dto.LoginDTO;

public interface AutenticacionServicios {
    DatosJWTtoken login(LoginDTO loginDTO)throws Exception;
}
