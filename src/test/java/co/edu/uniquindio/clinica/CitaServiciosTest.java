package co.edu.uniquindio.clinica;

import co.edu.uniquindio.clinica.dto.admin.HorarioDTO;
import co.edu.uniquindio.clinica.dto.admin.RegistroMedicoDTO;
import co.edu.uniquindio.clinica.dto.cita.*;
import co.edu.uniquindio.clinica.dto.paciente.FiltroBusquedaDTO;
import co.edu.uniquindio.clinica.dto.paciente.RegistroPacienteDTO;
import co.edu.uniquindio.clinica.model.*;
import co.edu.uniquindio.clinica.servicios.impl.CitaServiciosImpl;
import co.edu.uniquindio.clinica.servicios.impl.MedicoServicioImpl;
import co.edu.uniquindio.clinica.servicios.impl.PacienteServicioImpl;
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
public class CitaServiciosTest {
    @Autowired
    CitaServiciosImpl citaServicios;
    @Autowired
    PacienteServicioImpl pacienteServicio;
    @Autowired
    MedicoServicioImpl medicoServicio;

    @Test
    public void agendarCitaTest() {
        List<HorarioDTO> horarios = new ArrayList<>();
        int paciente = pacienteServicio.registrarse(new RegistroPacienteDTO("24567234", "Pepito Perez", "5454545", "url_foto", Ciudad.ARMENIA, LocalDate.of(2000,10,10), "Sin alergias", Eps.ALIANSALUD, TipoSangre.A_NEGATIVO, "pepito@email.com", "123"));
        int medico = medicoServicio.crearMedico(new RegistroMedicoDTO("Zayra Parra", "768786", Ciudad.ARMENIA, Especialidad.PEDIATRIA,"879896", "zay@gmail.com","111", "url-foto",horarios));
        AgendarCitaDTO agendarCitaDTO = new AgendarCitaDTO(
                paciente,
                medico,
                LocalDateTime.now(),
                LocalDateTime.of(2024,01,10,10,30),
                "motivo",
                Especialidad.PEDIATRIA
        );
        DetalleCitaDTO detalleCita = citaServicios.agendarCita(agendarCitaDTO);
        Assertions.assertNotEquals(0, detalleCita.codigoCita());
    }


   /* @Test
    @Sql("classpath:dataset.sql")
    public void cancelarCitaTest() {

        CancelamientoCitaDTO datos = new CancelamientoCitaDTO(
                1,
                MotivoCancelamiento.PASIENTE_DESISTIO
        );
        citaServicios.cancelarCita(datos);
    }*/

    @Test
    @Sql("classpath:dataset.sql")
    public void listarCitasTest() {
        List<ItemCitaDTO> lista = citaServicios.listarCitas();
        lista.forEach(System.out::println);
        Assertions.assertEquals(5, lista.size());
    }


    @Test
    @Sql("classpath:dataset.sql")
    public void listarHistorialPacienteTest() {
        List<ItemCitaDTO> lista = citaServicios.listarHistorialPaciente(6);
        lista.forEach(System.out::println);
        Assertions.assertEquals(1, lista.size());

    }

    @Test
    @Sql("classpath:dataset.sql")
    public void verDetalleCita() {
        DetalleAtencionMedicaDTO detalle = citaServicios.verDetalleCita(6);
        System.out.println(detalle);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void filtrarCitasPorMedicoTest() {
        List<ItemCitaDTO> lista = citaServicios.filtrarCitasPorMedico(26);
        lista.forEach(System.out::println);
        Assertions.assertEquals(3, lista.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void filtrarCitasPorFechaTest() {
        FiltroBusquedaDTO filtro = new FiltroBusquedaDTO(
                6,
                LocalDateTime.of(2023-10, 10, 1,10,20),
                LocalDateTime.of(2023, 12,01,10,30)

        );
        List<ItemCitaDTO> lista = citaServicios.filtrarCitasPorFecha(filtro);
        lista.forEach(System.out::println);
        Assertions.assertEquals(1, lista.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void filtrarCitaspendientesMedico() {
        List<ItemCitaDTO> lista = citaServicios.filtrarCitasPorMedico(6);
        lista.forEach(System.out::println);
        Assertions.assertEquals(2, lista.size());
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void listarCitasPendientesPacienteTest() {
        List<ItemCitaDTO> lista = citaServicios.listarCitasPendientesPaciente(6);
        lista.forEach(System.out::println);
        Assertions.assertEquals(1, lista.size());

    }
}
