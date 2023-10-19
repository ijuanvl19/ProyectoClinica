package co.edu.uniquindio.clinica.servicios.interfaces;

import co.edu.uniquindio.clinica.dto.medico.DiaLibreDTO;

import java.util.List;

public interface DiaLibreServicios {
    int crear(DiaLibreDTO diaLibreDTO);
    List<DiaLibreDTO> listar();
    DiaLibreDTO update(DiaLibreDTO datos);
    String eliminar(int codigo);
    DiaLibreDTO obtener(int Codigo);
}
