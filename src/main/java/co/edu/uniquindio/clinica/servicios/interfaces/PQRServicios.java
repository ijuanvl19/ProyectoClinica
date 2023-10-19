package co.edu.uniquindio.clinica.servicios.interfaces;

import co.edu.uniquindio.clinica.dto.pqrs.DetallePQRSDTO;
import co.edu.uniquindio.clinica.dto.pqrs.ItemPQRSDTO;
import co.edu.uniquindio.clinica.dto.RegistroRespuestaDTO;
import co.edu.uniquindio.clinica.dto.pqrs.RegistroPQRDTO;
import co.edu.uniquindio.clinica.model.EstadoPQRS;

import java.util.List;

public interface PQRServicios {
    //paciente
    //No se podrá tener activa o en progreso más de tres PQRS al mismo tiempo por paciente.
    //validar: quien está creando la PQR, mirar que el que la está creando no tenga más de 3 activas
    int crearPQRS(RegistroPQRDTO datos);
    /*Al crear la petición, automáticamente pasa a un estado de activa.*/
    void cambiarEstadoPQRS(int codigoPQRS, EstadoPQRS estadoPQRS);
    int responderPQRS(RegistroRespuestaDTO registroRespuestaDTO);
    List<ItemPQRSDTO>listarPQRS();
    /*Mientras las peticiones se encuentren en progreso,
    el paciente podrá dar respuesta a lo que haya dicho el
    administrador para ampliar los detalles.
     */
    DetallePQRSDTO verDetallePQRS(int codigo);
    //medico
    //admin
}
