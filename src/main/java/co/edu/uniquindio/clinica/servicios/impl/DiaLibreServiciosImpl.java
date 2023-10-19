package co.edu.uniquindio.clinica.servicios.impl;

import co.edu.uniquindio.clinica.dto.medico.DiaLibreDTO;
import co.edu.uniquindio.clinica.infra.errors.ValidacionDeIntegridadE;
import co.edu.uniquindio.clinica.model.*;
import co.edu.uniquindio.clinica.repositorios.DiaLibreRepo;
import co.edu.uniquindio.clinica.repositorios.MedicoRepo;
import co.edu.uniquindio.clinica.servicios.interfaces.DiaLibreServicios;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaLibreServiciosImpl implements DiaLibreServicios {
    private final DiaLibreRepo diaLibreRepo;
    private final MedicoRepo medicoRepo;
    @Override
    public int crear(DiaLibreDTO diaLibreDTO) {
        Medico medico = validarMedico(diaLibreDTO.codigoMedico());
        DiaLibre diaLibre = diaLibreRepo.save(new DiaLibre(diaLibreDTO,medico));
        System.out.println( "Dia Libre " + diaLibre.getCodigo() + " agendado");
        return diaLibre.getCodigo();
    }

    @Override
    public List<DiaLibreDTO> listar() {
        List<DiaLibre> diaLibres = diaLibreRepo.findAll();
        if (diaLibres.isEmpty()) {
            throw new ValidationException("No hay dias libres registrados");
        }
        return diaLibres.stream()
                .map(DiaLibreDTO::new)
                .toList();
    }

    @Override
    public DiaLibreDTO update(DiaLibreDTO datos) {
        DiaLibre diaLibre = validarDia(datos.codigo());
        Medico medico = validarMedico(datos.codigoMedico());
        diaLibre.actualizar(datos, medico);
        return new DiaLibreDTO(diaLibre);
    }

    @Override
    public String eliminar(int codigo) {
        DiaLibre diaLibre = validarDia(codigo);
        diaLibreRepo.delete(diaLibre);
        return "Médico eliminado con éxito";

    }

    @Override
    public DiaLibreDTO obtener(int codigo) {
        DiaLibre diaLibre = validarDia(codigo);
        return new DiaLibreDTO(diaLibre);
    }

    private Medico validarMedico(int codigo) {
        Optional<Medico> opcional = medicoRepo.findById(codigo);
        if (opcional.isEmpty()) {
            throw new ValidationException("No se encontró el médico con código "+codigo);
        }
        return opcional.get();
    }

    private DiaLibre validarDia (int codigo){
        Optional<DiaLibre> opcional = diaLibreRepo.findById(codigo);
        if( opcional.isEmpty() ){
            throw new ValidacionDeIntegridadE("No existe un dia libre con el código "+codigo);
        }
        return opcional.get();
    }
}
