package customExceptions;
/**
 *
 * Puede suceder que un usuario sea dado de baja por algún motivo importante
 * que el hotel considere. Si se desea realizar una operación con los datos
 * del usuario se lanzará esta excepción advirtiendo que no se puede realizar
 * lo requerido dado que el usuario fue dado de baja.El sistema presenta las
 * posibilidades de darlo de alta nuevamente.
 *
 */
public class UsuarioEnBajaException extends RuntimeException {
    public UsuarioEnBajaException(String mensaje){
        super(mensaje);
    }
}