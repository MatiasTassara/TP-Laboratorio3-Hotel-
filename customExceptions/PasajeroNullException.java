package customExceptions;
/**
 *
 * Cuando necesitamos un objeto de tipo Pasajero que se encuentra en Base de Datos pero
 * la correspondiente colección está vacía y el método encargado de tal operación nos
 * devuelve un null. Es ahí donde se lanza esta excepción informando que no estamos
 * contando con ese objeto de tipo Pasajero porque tenemos un valor nulo en su lugar.
 *
 */
public class PasajeroNullException extends RuntimeException {

    public PasajeroNullException(String mensaje)
    {
        super(mensaje);
    }

}