package co.edu.uniquindio.clinica.dto.medico;

public record RegistroAtencionDTO(
        int codigoCita,
        int codigoMedico,
        String notasMedicas,
        String tratamiento,
        String diagnostico
) {
}
