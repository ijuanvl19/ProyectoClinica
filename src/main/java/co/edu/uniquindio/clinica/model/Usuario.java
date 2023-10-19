package co.edu.uniquindio.clinica.model;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Usuario extends Cuenta {
    @Column(nullable = false, length=30)
    private Ciudad ciudad;

    @Column(nullable = false, length = 10, unique = true)
    private String cedula;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(nullable = false, length = 20, unique = true)
    private String telefono;

    @Lob
    @Column(nullable = false, unique = true)
    private String urlFoto;

    @Column(nullable = false)
    private EstadoUsuario estado = EstadoUsuario.ACTIVO;
}
