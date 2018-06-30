package clases;
/**
 * Exception que se lanza cuando el usuario del programa ingresa un password incorrecto.
 * Se realiza una comparación con la clave existente y en el caso de no ser igual se
 * lanza la excepción.
 *
 *
 */
public class ClaveIncorrectaException extends RuntimeException {
    public ClaveIncorrectaException(String mensaje){
        super(mensaje);
    }
}