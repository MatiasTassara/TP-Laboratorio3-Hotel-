package customExceptions;
/**
 *
 * Cuando el pasajero llega al hotel con un número de huéspedes superior a la capacidad
 * que tiene el hotel en el momento dado de la reserva, lanza la excepción advirtiendo
 * que esa cantidad sobrepasa la capacidad hotelera.
 *
 */
public class FaltaDisponibilidadException extends RuntimeException{

    public FaltaDisponibilidadException(String mensaje)
    {
        super(mensaje);
    }

}