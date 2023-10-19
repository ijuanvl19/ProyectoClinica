package co.edu.uniquindio.clinica.servicios.validaciones.agendar;

import co.edu.uniquindio.clinica.repositorios.CitaRepo;
import co.edu.uniquindio.clinica.dto.cita.AgendarCitaDTO;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MedicoConConsulta implements ValidadorDeCitas {
    private final CitaRepo citaRepo;
    @Override
    public void validar(AgendarCitaDTO datos) {
        if (datos.idMedico()==null)
            return;
        var medicoConConsulta = citaRepo.existsByMedicoCodigoAndFechaCita(
                datos.idMedico(), datos.fecha()
        );
        if (medicoConConsulta){
            throw new ValidationException("Este médico ya tiene una consulta en ese horario");
        }
    }
}
