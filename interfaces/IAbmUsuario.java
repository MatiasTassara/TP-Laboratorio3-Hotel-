package interfaces;

/**
 * Interfaz para dar de alta usuarios en el sistema, darlos de baja o pasarlos de baja a alta de nuevo.
 * El administrador da de alta a los concerjes, y los concerjes a los pasajeros.
 */
public interface IAbmUsuario {
    public void crearUsuario();
    public void darDeAltaUsuario(String dni);

    public void darDeBajaUsuario(String dni);
}