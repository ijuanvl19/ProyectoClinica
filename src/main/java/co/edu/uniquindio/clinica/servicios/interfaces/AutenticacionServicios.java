package co.edu.uniquindio.clinica.servicios.interfaces;

import co.edu.uniquindio.clinica.dto.security.DatosJWTtoken;
import co.edu.uniquindio.clinica.dto.security.LoginDTO;

public interface AutenticacionServicios {
    DatosJWTtoken login(LoginDTO loginDTO)throws Exception;
}
