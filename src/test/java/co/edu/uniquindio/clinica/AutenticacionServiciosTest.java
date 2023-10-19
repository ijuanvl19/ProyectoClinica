package co.edu.uniquindio.clinica;

import co.edu.uniquindio.clinica.dto.security.DatosJWTtoken;
import co.edu.uniquindio.clinica.dto.security.LoginDTO;
import co.edu.uniquindio.clinica.servicios.impl.AutenticacionServicioImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Transactional
public class AutenticacionServiciosTest {
    @Autowired
    AutenticacionServicioImpl autenticacionServicio;

    @Test
    @Sql("classpath:dataset.sql")
    public void loginTest() throws Exception {
        LoginDTO sesionDTO = new LoginDTO(
                "nuevo@email.com",
                "juan"
        );
        DatosJWTtoken tokenDTO = autenticacionServicio.login(sesionDTO);
        System.out.println(tokenDTO.jwtToken());
        Assertions.assertNotEquals(null, tokenDTO.jwtToken());
    }
}
