package co.edu.uniquindio.clinica;

import co.edu.uniquindio.clinica.dto.admin.HorarioDTO;
import co.edu.uniquindio.clinica.dto.admin.RegistroMedicoDTO;
import co.edu.uniquindio.clinica.dto.medico.DiaLibreDTO;
import co.edu.uniquindio.clinica.model.Ciudad;
import co.edu.uniquindio.clinica.model.Especialidad;
import co.edu.uniquindio.clinica.servicios.impl.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class DiaLibreServiciosTest {
    @Autowired
    private DiaLibreServiciosImpl diaLibreServicios;
    @Autowired
    private MedicoServicioImpl medicoServicio;

    @Test
    public void crearTest() throws Exception {
        List<HorarioDTO> horarios = new ArrayList<>();
        int medico = medicoServicio.crearMedico(new RegistroMedicoDTO("Zayra Parra", "768786", Ciudad.ARMENIA, Especialidad.PEDIATRIA,"879896", "zay@gmail.com","111", "url-foto",horarios));
        DiaLibreDTO diaLibreDTO = new DiaLibreDTO(
                1,
                medico,
                LocalDate.of(2023,12,15)

        );
        int nuevo = diaLibreServicios.crear(diaLibreDTO);
        Assertions.assertNotEquals(0, nuevo);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void updateTest() throws Exception {
        DiaLibreDTO guardado = diaLibreServicios.obtener(6);
        DiaLibreDTO modificado = new DiaLibreDTO(
                guardado.codigo(),
                guardado.codigoMedico(),
                LocalDate.of(2024,12,15)
        );
        diaLibreServicios.update(modificado);
        DiaLibreDTO objetoModificado = diaLibreServicios.obtener(6);
        Assertions.assertEquals(LocalDate.of(2024,12,15), objetoModificado.fecha());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarTest() throws Exception {
        diaLibreServicios.eliminar(6);
        Assertions.assertThrows(Exception.class, () -> diaLibreServicios.obtener(1));
    }
    @Test
    @Sql("classpath:dataset.sql")
    public void listarTest() {
        List<DiaLibreDTO> lista = diaLibreServicios.listar();
        lista.forEach(System.out::println);
        Assertions.assertEquals(5, lista.size());
    }
}
