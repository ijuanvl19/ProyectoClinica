package co.edu.uniquindio.clinica;

import co.edu.uniquindio.clinica.dto.RegistroRespuestaDTO;
import co.edu.uniquindio.clinica.dto.admin.HorarioDTO;
import co.edu.uniquindio.clinica.dto.admin.RegistroMedicoDTO;
import co.edu.uniquindio.clinica.dto.cita.AgendarCitaDTO;
import co.edu.uniquindio.clinica.dto.cita.DetalleCitaDTO;
import co.edu.uniquindio.clinica.dto.paciente.RegistroPacienteDTO;
import co.edu.uniquindio.clinica.dto.pqrs.DetallePQRSDTO;
import co.edu.uniquindio.clinica.dto.pqrs.ItemPQRSDTO;
import co.edu.uniquindio.clinica.dto.pqrs.RegistroPQRDTO;
import co.edu.uniquindio.clinica.model.*;
import co.edu.uniquindio.clinica.servicios.impl.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class PQRSServiciosTest {
    @Autowired
    private PQRServicioImpl pqrServicio;
    @Autowired
    private PacienteServicioImpl pacienteServicio;
    @Autowired
    private MedicoServicioImpl medicoServicio;
    @Autowired
    private CitaServiciosImpl citaServicios;
    @Autowired
    private MensajeServiciosImpl mensajeServicios;

    @Test
    public void crearPQRS() {
        List<HorarioDTO> horarios = new ArrayList<>();
        int paciente = pacienteServicio.registrarse(new RegistroPacienteDTO("24567234", "Pepito Perez", "5454545", "url_foto", Ciudad.ARMENIA, LocalDate.of(2000,10,10), "Sin alergias", Eps.ALIANSALUD, TipoSangre.A_NEGATIVO, "pepito@email.com", "123"));
        int medico = medicoServicio.crearMedico(new RegistroMedicoDTO("Zayra Parra", "768786", Ciudad.ARMENIA, Especialidad.PEDIATRIA,"879896", "zay@gmail.com","111", "url-foto",horarios));
        DetalleCitaDTO cita = citaServicios.agendarCita(new AgendarCitaDTO(paciente,medico,LocalDateTime.now(), LocalDateTime.of(2023,10,30,10,30), "motivo", EstadoCita.PROGRAMADA, Especialidad.PSIQUIATRIA));
        RegistroPQRDTO datos = new RegistroPQRDTO(
                cita.codigoCita(),
                "motivo",
                paciente,
                "tipo",
                EstadoPQRS.NUEVO,
                LocalDateTime.now()

        );
        int nuevo = pqrServicio.crearPQRS(datos);
        System.out.println(pqrServicio.verDetallePQRS(nuevo));
        Assertions.assertNotEquals(0, nuevo);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void cambiarEstadoPQRS() {
        DetallePQRSDTO guardado = pqrServicio.verDetallePQRS(1);
        pqrServicio.cambiarEstadoPQRS(
                guardado.codigo(),
                EstadoPQRS.RESUELTO
        );
        DetallePQRSDTO objetoModificado = pqrServicio.verDetallePQRS(1);
        Assertions.assertEquals(EstadoPQRS.RESUELTO, objetoModificado.estado());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarPQRS() {
        List<ItemPQRSDTO> lista = pqrServicio.listarPQRS();
        lista.forEach(System.out::println);
        Assertions.assertEquals(5, lista.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void responderPQRS() {/*
        List<HorarioDTO> horarios = new ArrayList<>();
        int paciente = pacienteServicio.registrarse(new RegistroPacienteDTO("24567234", "Pepito Perez", "5454545", "url_foto", Ciudad.ARMENIA, LocalDate.of(2000,10,10), "Sin alergias", Eps.ALIANSALUD, TipoSangre.A_NEGATIVO, "pepito@email.com", "123"));
        int medico = medicoServicio.crearMedico(new RegistroMedicoDTO("Zayra Parra", "768786", Ciudad.ARMENIA, Especialidad.PEDIATRIA,"879896", "zay@gmail.com","111", "url-foto",horarios));
        DetalleCitaDTO cita = citaServicios.agendarCita(new AgendarCitaDTO(paciente,medico,LocalDateTime.now(), LocalDateTime.of(2023,10,30,10,30), "motivo", EstadoCita.PROGRAMADA, Especialidad.PSIQUIATRIA));
        int pqrs = pqrServicio.crearPQRS(new RegistroPQRDTO(cita.codigoCita(),"motivo", paciente, "tipo",EstadoPQRS.ENPROCESO, LocalDateTime.now()));
        int mensaje = mensajeServicios.crear(new RegistroRespuestaDTO(paciente,pqrs,1,"Hola"));*/
        RegistroRespuestaDTO respuesta = new RegistroRespuestaDTO(
                1,
                1,
                1,//???????
                "Como estás"

        );
        int nuevo = pqrServicio.responderPQRS(respuesta);
        String primerMensaje = mensajeServicios.obtener(respuesta.codigoMensaje()).mensajesAsociados();
        String segundoMensaje = mensajeServicios.obtener(respuesta.codigoMensaje()).contenido();
        System.out.println(primerMensaje);
        System.out.println(segundoMensaje);
        Assertions.assertNotEquals(0, nuevo);
    }
}
