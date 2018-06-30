package interfaces;

/**
 * Interfaz  para dar de alta o baja  una habitacion de hotel segun el usuario que la implemente
 * sera distinta la forma de dar de alta.
 * El Administrador crea habitaciones y elimina segun modificaciones fisicas en el hotel y
 * el concerje las da de baja o alta segun si se encuentran habilitadas para su uso
 */
public interface IAbmHabitacion {
    public void darAltaHab(String numeroHab);
    public void darBajaHab(String numeroHab);
}