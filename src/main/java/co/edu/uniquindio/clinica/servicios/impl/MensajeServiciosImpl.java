package co.edu.uniquindio.clinica.servicios.impl;

import co.edu.uniquindio.clinica.dto.RegistroRespuestaDTO;
import co.edu.uniquindio.clinica.dto.RespuestaDTO;
import co.edu.uniquindio.clinica.infra.errors.ValidacionDeIntegridadE;
import co.edu.uniquindio.clinica.model.Cuenta;
import co.edu.uniquindio.clinica.model.Mensaje;
import co.edu.uniquindio.clinica.model.Pqrs;
import co.edu.uniquindio.clinica.repositorios.CuentaRepo;
import co.edu.uniquindio.clinica.repositorios.MensajeRepo;
import co.edu.uniquindio.clinica.repositorios.PQRSRepo;
import co.edu.uniquindio.clinica.servicios.interfaces.MensajeServicios;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MensajeServiciosImpl implements MensajeServicios {
    private final MensajeRepo mensajeRepo;
    private final PQRSRepo pqrsRepo;
    private final CuentaRepo cuentaRepo;
    @Override
    public int crear(RegistroRespuestaDTO datos) {
        //Obtener el PQRS
        Optional<Pqrs> opcionalPQRS = pqrsRepo.findById(datos.codigoPQRS());

        if (opcionalPQRS.isEmpty()) {
            throw new ValidationException("No existe un PQRS con el código "+datos.codigoPQRS());
        }
        //Obtener la cuenta
        Optional<Cuenta> opcionalCuenta = cuentaRepo.findById(datos.codigoCuenta());

        if (opcionalCuenta.isEmpty()) {
            throw new ValidationException("No existe una cuenta con el código "+datos.codigoCuenta());
        }
        /*//obtener el mensaje
        Optional<Mensaje> opcionalMensaje = mensajeRepo.findById(datos.codigoCuenta());
        if (opcionalMensaje.isEmpty()) {
            throw new ValidationException("No existe un mensaje con el código "+datos.codigoMensaje());
        }*/
        Mensaje mensaje = mensajeRepo.save(new Mensaje(
                datos,
                LocalDateTime.now(),
                opcionalPQRS.get(),
                //opcionalMensaje.get(),
                opcionalCuenta.get()));

        System.out.println("Mensaje " + mensaje.getCodigo() + " creado");
        return mensaje.getCodigo();
    }

    @Override
    public List<RespuestaDTO> listar() {
        return null;
    }

    @Override
    public RespuestaDTO update(String contenido) {
        return null;
    }

    @Override
    public int eliminar(int codigo) {
        return 0;
    }

    @Override
    public RespuestaDTO obtener(int codigo) {
        Mensaje mensaje  = validar(codigo);
        return new RespuestaDTO(mensaje);
    }
    private Mensaje validar(int codigo){
        Optional<Mensaje> opcional = mensajeRepo.findById(codigo);
        if( opcional.isEmpty() ){
            throw new ValidacionDeIntegridadE("No existe un mensaje con el código "+codigo);
        }
        return opcional.get();
    }
}
