package customExceptions;
/**
 *
 * Para el trabajo con ArrayList, cada vez que se realiza una modificación se hace sobre un auxiliar,
 * que luego es ingresado a la colección que se encuentra en la Base de Datos. Al tratarse de un
 * ArrayList, necesitamos el índice para pisar la versión anterior del objeto. La excepción es lanzada
 * si la colección está vacía y el método nos devuelve como valor un índice igual a -1.
 *
 */
public class IndiceIncorrectoException extends RuntimeException{

    public IndiceIncorrectoException(String mensaje)
    {
        super(mensaje);
    }

}