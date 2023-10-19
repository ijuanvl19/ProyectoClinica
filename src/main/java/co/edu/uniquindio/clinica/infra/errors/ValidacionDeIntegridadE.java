package co.edu.uniquindio.clinica.infra.errors;

//Es el mismo ResourseNotFoundException
public class ValidacionDeIntegridadE extends RuntimeException {
    public ValidacionDeIntegridadE(String s) {
        super(s);
    }
}
