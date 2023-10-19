package co.edu.uniquindio.clinica;

import co.edu.uniquindio.clinica.dto.admin.HorarioDTO;
import co.edu.uniquindio.clinica.dto.admin.RegistroMedicoDTO;
import co.edu.uniquindio.clinica.model.Ciudad;
import co.edu.uniquindio.clinica.model.Especialidad;
import co.edu.uniquindio.clinica.servicios.interfaces.AdministradorServicios;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional//para que al ejecutar los métodos de test no modifiquen la base de datos de MySQL.
public class AdministradorServiciosTest {

    @Autowired
    private AdministradorServicios administradorServicio;


    @Test
    public void crearMedicoTest(){

        List<HorarioDTO> horarios = new ArrayList<>();
        horarios.add( new HorarioDTO("LUNES", LocalTime.of(7, 0, 0), LocalTime.of(14, 0, 0) ) );

        RegistroMedicoDTO medicoDTO = new RegistroMedicoDTO(
                "Juanito",
                "74658",
                Ciudad.MEDELLIN,
                Especialidad.PEDIATRIA,
                "65432",
                "juanita@email.com",
                "password123",
                "url_foto",
                horarios
        );
        System.out.println(medicoDTO);

        try {
            administradorServicio.crearMedico(medicoDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
