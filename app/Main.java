package app;

import clases.BaseDeDatos;
import clases.Concerje;
import clases.Hotel;

public class Main {

    public static void main(String[] args) {

        Hotel hotel = Hotel.getSingletonInstance("Hotel","calle 123","2222222");
        Concerje concerje = new Concerje("123","pepe","lopez", "abc");
        BaseDeDatos.agregarConcerje(concerje);
        BaseDeDatos.levantarAllCollections();
        hotel.getLoginMenu().cargarMapaUsuarios();
        hotel.getLoginMenu().login();
    }
}