package co.edu.uniquindio.clinica.model;

import co.edu.uniquindio.clinica.dto.admin.DetalleMedicoDTO;
import co.edu.uniquindio.clinica.dto.admin.RegistroMedicoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medico extends Usuario implements Serializable {
    @Column(nullable = false)
    //@Enumerated(EnumType.STRING)//Para guardar el String como tal del enum.
    private Especialidad especialidad;

    @OneToMany(mappedBy = "medico")
    private List<DiaLibre> diasLibres;

    @OneToMany(mappedBy = "medico")
    private List<HorarioMedico> horarios;

    @OneToMany(mappedBy = "medico")
    private List<Cita> citas;

    public Medico(RegistroMedicoDTO medicoDTO){
        this.setCedula(medicoDTO.cedula());
        this.setTelefono(medicoDTO.telefono());
        this.setNombre(medicoDTO.nombre());
        this.setEspecialidad(medicoDTO.especialidad());
        this.setCiudad(medicoDTO.ciudad());
        this.setCorreo(medicoDTO.correo());
        this.setPasswd(medicoDTO.password());
        this.setUrlFoto(medicoDTO.urlFoto());
        this.setEstado(EstadoUsuario.ACTIVO);
    }
    public void actualizar(DetalleMedicoDTO medicoDTO){
        this.setCedula(medicoDTO.cedula());
        this.setTelefono(medicoDTO.telefono());
        this.setNombre(medicoDTO.nombre());
        this.setEspecialidad(medicoDTO.especialidad());
        this.setCiudad(medicoDTO.ciudad());
        this.setCorreo(medicoDTO.correo());
        this.setUrlFoto(medicoDTO.urlFoto());
    }
}
