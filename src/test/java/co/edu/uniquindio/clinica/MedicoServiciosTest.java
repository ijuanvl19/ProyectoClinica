package co.edu.uniquindio.clinica;

import co.edu.uniquindio.clinica.dto.admin.DetalleMedicoDTO;
import co.edu.uniquindio.clinica.dto.admin.HorarioDTO;
import co.edu.uniquindio.clinica.dto.admin.ItemMedicoDto;
import co.edu.uniquindio.clinica.dto.admin.RegistroMedicoDTO;
import co.edu.uniquindio.clinica.dto.cita.AgendarCitaDTO;
import co.edu.uniquindio.clinica.dto.cita.DetalleCitaDTO;
import co.edu.uniquindio.clinica.dto.cita.ItemCitaDTO;
import co.edu.uniquindio.clinica.dto.medico.DiaLibreDTO;
import co.edu.uniquindio.clinica.dto.medico.RegistroAtencionDTO;
import co.edu.uniquindio.clinica.dto.paciente.RegistroPacienteDTO;
import co.edu.uniquindio.clinica.model.*;
import co.edu.uniquindio.clinica.servicios.impl.AtencionServicioImpl;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class MedicoServiciosTest {
    @Autowired
    private MedicoServicioImpl medicoServicio;
    @Autowired
    private AtencionServicioImpl atencionServicio;
    @Autowired
    private PacienteServicioImpl pacienteServicio;
    @Autowired
    private CitaServiciosImpl citaServicios;


    @Test
    public void crearMedicoTest() {
        List<HorarioDTO> horarios = new ArrayList<>();
        horarios.add( new HorarioDTO("LUNES", LocalTime.of(7, 0, 0), LocalTime.of(14, 0, 0) ) );

        RegistroMedicoDTO medicoDTO = new RegistroMedicoDTO(
                "Pepito",
                "82872",
                Ciudad.ARMENIA,
                Especialidad.CARDIOLOGIA,
                "78387",
                "pepito@email.com",
                "123a",
                "url_foto",
                horarios
        );
        int nuevo = medicoServicio.crearMedico(medicoDTO);
        Assertions.assertNotEquals(0, nuevo);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void acualizarMedico() throws Exception {
        DetalleMedicoDTO guardado = medicoServicio.obtenerMedico(6);
        DetalleMedicoDTO modificado = new DetalleMedicoDTO(
                guardado.codigo(),
                guardado.cedula(),
                guardado.nombre(),
                guardado.ciudad(),
                guardado.especialidad(),
                "111111",
                guardado.correo(),
                guardado.urlFoto(),
        guardado.horarios()
        );

        medicoServicio.acualizarMedico(modificado);
        DetalleMedicoDTO objetoModificado = medicoServicio.obtenerMedico(6);
        Assertions.assertEquals("111111", objetoModificado.telefono());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarMedico() throws Exception {
        medicoServicio.eliminarMedico(6);
        Assertions.assertThrows(Exception.class, () -> medicoServicio.obtenerMedico(6));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarMedicos() throws Exception {
        List<ItemMedicoDto> lista = medicoServicio.listarMedicos();
        lista.forEach(System.out::println);
        Assertions.assertEquals(5, lista.size());
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void listarCitasPendientesTest() {
        List<ItemCitaDTO> lista = medicoServicio.listarCitasPendientes(6);
        lista.forEach(System.out::println);
        Assertions.assertEquals(1, lista.size());
    }
    @Test
    public void atenderCitaTest() throws Exception {
        List<HorarioDTO> horarios = new ArrayList<>();
        int paciente = pacienteServicio.registrarse(new RegistroPacienteDTO("24567234", "Pepito Perez", "5454545", "url_foto", Ciudad.ARMENIA, LocalDate.of(2000,10,10), "Sin alergias", Eps.ALIANSALUD, TipoSangre.A_NEGATIVO, "pepito@email.com", "123"));
        int medico = medicoServicio.crearMedico(new RegistroMedicoDTO("Zayra Parra", "768786", Ciudad.ARMENIA, Especialidad.PEDIATRIA,"879896", "zay@gmail.com","111", "url-foto",horarios));
        DetalleCitaDTO cita = citaServicios.agendarCita(new AgendarCitaDTO(paciente,medico, LocalDateTime.now(), LocalDateTime.of(2023,10,30,10,30), "motivo", EstadoCita.PROGRAMADA, Especialidad.PSIQUIATRIA));
        RegistroAtencionDTO registroAtencionDTO = new RegistroAtencionDTO(
                cita.codigoCita(),
                medico,
                "notas",
                "tratamiento",
                "diagnóstico enfermedad"

        );
        int atencion = medicoServicio.atenderCita(registroAtencionDTO);
        Assertions.assertNotEquals(0, atencion);
    }

    @Test
    public void agendarDiaLibreTest() throws Exception {
        List<HorarioDTO> horarios = new ArrayList<>();
        int medico = medicoServicio.crearMedico(new RegistroMedicoDTO("Zayra Parra", "768786", Ciudad.ARMENIA, Especialidad.PEDIATRIA,"879896", "zay@gmail.com","111", "url-foto",horarios));
        DiaLibreDTO diaLibreDTO = new DiaLibreDTO(
                1,
                medico,
                LocalDate.of(2023,12,30)

        );
        int dia = medicoServicio.agendarDiaLibre(diaLibreDTO);
        Assertions.assertNotEquals(0, dia);
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void listarCitasRealizadasMedicoTest() throws Exception {
        List<ItemCitaDTO> lista = medicoServicio.listarCitasRealizadasMedico(6);
        lista.forEach(System.out::println);
        Assertions.assertEquals(1, lista.size());
    }

}
