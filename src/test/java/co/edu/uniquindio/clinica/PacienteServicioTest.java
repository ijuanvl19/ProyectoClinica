package co.edu.uniquindio.clinica;

import co.edu.uniquindio.clinica.dto.cita.ItemCitaDTO;
import co.edu.uniquindio.clinica.dto.paciente.DetallePacienteDTO;
import co.edu.uniquindio.clinica.dto.paciente.FiltroBusquedaDTO;
import co.edu.uniquindio.clinica.dto.paciente.ItemPacienteDTO;
import co.edu.uniquindio.clinica.dto.paciente.RegistroPacienteDTO;
import co.edu.uniquindio.clinica.dto.pqrs.ItemPQRSDTO;
import co.edu.uniquindio.clinica.model.Ciudad;
import co.edu.uniquindio.clinica.model.Eps;
import co.edu.uniquindio.clinica.model.TipoSangre;
import co.edu.uniquindio.clinica.servicios.impl.PacienteServicioImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
public class PacienteServicioTest {
    @Autowired
    private PacienteServicioImpl pacienteServicio;

    @Test
    public void reqistrarTest() throws Exception {
        //Creamos un objeto con los datos del paciente
        //List<Alergia> listaDeAlergias = List.of(Alergia.POLVO, Alergia.POLEN);
        RegistroPacienteDTO pacienteDTO = new RegistroPacienteDTO(
                "1193214475",
                "Juan Velez",
                "322256105",
                "www.urlfoto.com",
                Ciudad.MEDELLIN,
                LocalDate.of(2002, 3, 2),
                "Tengo rinitis",
                //listaDeAlergias,
                Eps.NUEVA_EPS,
                TipoSangre.A_POSITIVO,
                "juanp.velezl19@gmail.com",
                "12345");
        //Guardamos el registro usando el método del servicio
        int nuevo = pacienteServicio.registrarse(pacienteDTO);
        System.out.println(pacienteServicio.verDetallePaciente(nuevo));
        //Comprobamos que sí haya quedado guardado. Si se guardó debe retornar el código (no 0).
        Assertions.assertNotEquals(0, nuevo);
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void actualizarTest() throws Exception {
        //Para actualizar el paciente primero lo obtenemos
        DetallePacienteDTO guardado = pacienteServicio.verDetallePaciente(6);
        //Le modificamos el número de teléfono, lo demás lo dejamos igual
        DetallePacienteDTO modificado = new DetallePacienteDTO(
                guardado.codigo(),
                guardado.cedula(),
                guardado.nombre(),
                "111111",
                guardado.correo(),
                guardado.fechaNacimiento(),
                guardado.urlFoto(),
                guardado.ciudad(),
                guardado.eps(),
                guardado.tipoSangre(),
                guardado.alergias());
        //Se invoca el servicio de actualizar los datos
        pacienteServicio.editarPerfil(modificado);
        //Se obtiene nuevamente el paciente para comprobar que sí se haya actualizado
        DetallePacienteDTO objetoModificado = pacienteServicio.verDetallePaciente(6);
        //Se comprueba que el teléfono del paciente sea el que se le asignó en la actualización
        Assertions.assertEquals("111111", objetoModificado.telefono());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void eliminarTest() throws Exception {
        //Se borra por ejemplo el paciente con el código 1
        pacienteServicio.eliminarCuenta(6);
        //Si intentamos buscar un paciente con el código del paciente borrado debemos obtener una excepción indicando que ya no existe
        Assertions.assertThrows(Exception.class, () -> pacienteServicio.verDetallePaciente(6));
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarTest() {
        //Obtenemos la lista de todos los pacientes
        List<ItemPacienteDTO> lista = pacienteServicio.listarTodos();
        lista.forEach(System.out::println);
        //Si en el dataset creamos 5 pacientes, entonces el tamaño de la lista debe ser 5
        Assertions.assertEquals(5, lista.size());
    }
//solo lista las programadas
    @Test
    @Sql("classpath:dataset.sql")
    public void listarCitasPacienteTest() throws Exception {
        List<ItemCitaDTO> lista = pacienteServicio.listarCitasPaciente(6);
        lista.forEach(System.out::println);
        Assertions.assertEquals(1, lista.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void filtrarCitasPorFechaTest(){
        FiltroBusquedaDTO filtroBusquedaDTO = new FiltroBusquedaDTO(
                6,
                LocalDateTime.of(2023,10,12,10,30),
                LocalDateTime.of(2023,12,12,10,30)
        );
        List<ItemCitaDTO> lista = pacienteServicio.filtrarCitasPorFecha(filtroBusquedaDTO);
        lista.forEach(System.out::println);
        Assertions.assertEquals(1, lista.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void filtrarCitasPorMedicoTest() {
        List<ItemCitaDTO> lista = pacienteServicio.filtrarCitasPorMedico(26);
        lista.forEach(System.out::println);
        Assertions.assertEquals(3, lista.size());
    }

    @Test
    @Sql("classpath:dataset.sql")
    public void listarPQRSPacienteTest() {
        List<ItemPQRSDTO> lista = pacienteServicio.listarPQRSPaciente(6);
        lista.forEach(System.out::println);
        Assertions.assertEquals(1, lista.size());
    }
}
