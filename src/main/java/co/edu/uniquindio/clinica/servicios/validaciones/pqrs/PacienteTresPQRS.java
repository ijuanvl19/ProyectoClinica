package co.edu.uniquindio.clinica.servicios.validaciones.pqrs;

import co.edu.uniquindio.clinica.dto.pqrs.RegistroPQRDTO;
import co.edu.uniquindio.clinica.model.EstadoPQRS;
import co.edu.uniquindio.clinica.repositorios.PQRSRepo;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PacienteTresPQRS {
    private final PQRSRepo pqrsRepo;
    public void validar(RegistroPQRDTO datos) {
        //obtiene el num de PQRS activas o en proceso del paciente
        var numeroDePQRS = pqrsRepo.countByCita_PacienteCodigoAndEstadoAndEstado(datos.codigoPaciente(), EstadoPQRS.ENPROCESO, EstadoPQRS.NUEVO);
        if (numeroDePQRS>=3){
            throw new ValidationException("El paciente ya tiene 3 PQRS activas o en progreso. ");
        }


    }
}
