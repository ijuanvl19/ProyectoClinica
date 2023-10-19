package co.edu.uniquindio.clinica.servicios.impl;

import co.edu.uniquindio.clinica.dto.security.DatosJWTtoken;
import co.edu.uniquindio.clinica.dto.security.LoginDTO;
import co.edu.uniquindio.clinica.infra.security.TokenService;
import co.edu.uniquindio.clinica.model.Cuenta;
import co.edu.uniquindio.clinica.servicios.interfaces.AutenticacionServicios;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class AutenticacionServicioImpl implements AutenticacionServicios {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    @Override
    public DatosJWTtoken login(LoginDTO loginDTO) throws Exception {
        Authentication Autentoken = new UsernamePasswordAuthenticationToken(
                loginDTO.user(), loginDTO.passwd()
        );
        var usuarioAutenticado = authenticationManager.authenticate(Autentoken);
        var JWTtoken = tokenService.generarToken((Cuenta) usuarioAutenticado.getPrincipal());
        return new DatosJWTtoken(JWTtoken);//El controller recibirá este token  y hará un return ResponseEntity.ok(login)
    }
}
