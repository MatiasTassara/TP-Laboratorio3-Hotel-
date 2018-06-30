package customExceptions;
/**
 *
 * Excepción lanzada en caso de querer ingresar al sistema un usuario que no fue dado de alta previamente.
 * El sistema informa que con ese nombre de usuario no existe tal usuario con autorización
 * para ingresar y realizar operaciones.
 *
 */
public class UsuarioInexistenteException extends RuntimeException {

    public UsuarioInexistenteException(String mensaje){
        super(mensaje);
    }
}