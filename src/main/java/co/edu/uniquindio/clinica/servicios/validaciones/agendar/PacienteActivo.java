package co.edu.uniquindio.clinica.servicios.validaciones.agendar;

import co.edu.uniquindio.clinica.dto.cita.AgendarCitaDTO;
import co.edu.uniquindio.clinica.repositorios.PacienteRepo;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PacienteActivo implements ValidadorDeCitas {
    private final PacienteRepo pacienteRepo;
    @Override
    public void validar(AgendarCitaDTO datos) {
        if(datos.idPaciente()==null){
            return;
        }
        var pacienteActivo = pacienteRepo.findActivoById(datos.idPaciente());
        if (pacienteActivo==null){
            throw new ValidationException("No se permite agendar citas con pacientes inactivos " +
                    "en el sistema");
        }
    }
}
