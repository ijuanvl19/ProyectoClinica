package co.edu.uniquindio.clinica.model;

public enum TipoSangre {

    A_POSITIVO("A+"),
    A_NEGATIVO("A-");

    private String nombre;
    TipoSangre(String nombre){
        this.nombre = nombre;
    }

}
