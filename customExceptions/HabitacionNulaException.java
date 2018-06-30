package customExceptions;
/**
 * Cuando se pide una habitación a la Base de Datos pero la colección está vacía
 * y el método devuelve un null en vez de un objeto de tipo Habitacion.
 *
 *
 */
public class HabitacionNulaException extends RuntimeException{

    public HabitacionNulaException(String mensaje)
    {
        super(mensaje);
    }

}