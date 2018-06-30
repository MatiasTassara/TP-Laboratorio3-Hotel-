package clases;

import customExceptions.UsuarioInexistenteException;
import interfaces.IAbmHabitacion;
import interfaces.IAbmUsuario;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Esta clase representa a un administrador del hotel, que cumple el rol de gerente general. Tiene la funcion de crear las habitaciones,
 * darlas de baja, crear y dar de alta o baja a los concerjes, y realizar un backup del sistema (guarda en archivos la informacion que
 * toma de la base de datos. Para que cualquier cambio quede en memoria secundaria, el administrador debe realizar un backup.
 *  Tambien puede consultar las habitaciones, reservas, pasajeros y gerentes.
 */

public class Administrador extends Usuario implements IAbmUsuario, IAbmHabitacion,Serializable{
    public Administrador(){

    }

    public Administrador(String dni,String nombre,String apellido,String password) {
        super(dni,nombre,apellido,password);

    }

    /**
     * Este método descarga en una coleccion auxiliar los concerjes registrados en la base de datos,
     * para luego transcribirlos en el archivo corcerjes.dat. Posteriormente en un método general será llamado para
     * realizar el back up junto a otras colecciones. Si el archivo no existe lo crea.
     */
    public void serializarConcerjes () {

        ObjectOutputStream escritura = null;
        HashMap<String, Concerje> auxiliar  = BaseDeDatos.obtenerConcerjes();

        try {
            escritura = new ObjectOutputStream(new FileOutputStream("concerjes.dat"));
            escritura.writeObject(auxiliar);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                escritura.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Método que vuelca en una coleccion auxiliar de tipo Habitacion, las habitaciones que se encuentran en
     * la Base de Datos y que reemplazará a la existente en el archivo habitaciones.dat. Si el archivo no
     * existe lo crea.
     */
    public void serializarHabitaciones () {

        ObjectOutputStream escritura = null;
        ArrayList<Habitacion> auxiliar  = BaseDeDatos.obtenerHabitaciones();

        try {
            escritura = new ObjectOutputStream(new FileOutputStream("habitaciones.dat"));

            escritura.writeObject(auxiliar);


        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                escritura.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Método que vuelca en una colección auxiliar los pasajeros que se encuentran en la Base de Datos
     * y que serán escritos en el archivo pasajeros.dat, si el archivo no existe lo crea.
     */
    public void serializarPasajeros () {

        ObjectOutputStream escritura = null;
        HashMap<String, Pasajero> auxiliar  = BaseDeDatos.obtenerPasajeros();

        try {
            escritura = new ObjectOutputStream(new FileOutputStream("pasajeros.dat"));
            escritura.writeObject(auxiliar);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                escritura.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Método que vuelca en una colección auxiliar las reservas existentes en Base de Datos y que
     * serán luego grabadas en el archivo existente o no - si no existe lo crea - reservas.dat.
     */
    public void serializarReservas () {

        ObjectOutputStream escritura = null;
        ArrayList<Reserva> auxiliar  = BaseDeDatos.obtenerReservas();

        try {
            escritura = new ObjectOutputStream(new FileOutputStream("reservas.dat"));
            escritura.writeObject(auxiliar);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                escritura.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Método que vuelca en una colección auxiliar los administradores existentes en la Base de Datos
     * que posteriormente serán registrados en el archivo administradores.dat. En caso de no existir
     * tal archivo lo crea.
     */
    public void serializarAdministradores () {

        ObjectOutputStream escritura = null;
        HashMap<String,Administrador> auxiliar  = BaseDeDatos.obtenerAdministradores();

        try {
            escritura = new ObjectOutputStream(new FileOutputStream("administradores.dat"));
            escritura.writeObject(auxiliar);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                escritura.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Método que llama a los metodos serializables que se encargan de volcar en colecciones auxiliares
     * todo lo existente en base de datos y lo graba en los archivos correspondientes permitiendo
     * la persistencia de los datos.
     */
    public void backUp() {

        char seguir;

        System.out.println("Desea realizar un Back Up de la informacion del sistema? s/n");
        seguir = LoginMenu.scanner.next().charAt(0);
        if(seguir == 's' || seguir == 'S') {

            serializarHabitaciones();
            serializarPasajeros();
            serializarReservas();
            serializarConcerjes();
            serializarAdministradores();
        }

    }

    /**
     * Método que crear concerjes. Este método proviene de una interfaz implementada en el caso de
     * administradores y concerjes de hotel. Cada uno de los cuales da de alta diferentes tipos de usuarios.
     * En este caso, un administrador da de alta a un concerje, es decir a un empleado del hotel.
     */
    @Override
    public void crearUsuario() {
        String nombreConcerje = "";
        String apellidoConcerje = "";
        String dniConcerje = "";
        char correcto = 'n';
        Concerje concerje1;
        String pass = "";
        int contador = 0;

        while(correcto == 'n' || correcto == 'N') {
            System.out.println("**Alta de nuevo concerje**");
            System.out.println("Ingrese nombre(s): ");
            nombreConcerje = LoginMenu.scanner.next();
            System.out.println("Ingrese Apellido(s)");
            apellidoConcerje = LoginMenu.scanner.next();
            System.out.println("Ingrese DNI");
            dniConcerje = LoginMenu.scanner.next();
            System.out.println("Ingrese password");
            pass = LoginMenu.scanner.next();
            System.out.println("Los datos ingresados son: \nNombre: " + nombreConcerje + "\nApellido: " + apellidoConcerje +
                    "\nDNI: " + dniConcerje + "\nEs correcta la informacion? oprimir 's' para guardar... 'n' para modificar... ");
            correcto = LoginMenu.scanner.next().charAt(0);
        }
        concerje1 = new Concerje(dniConcerje,nombreConcerje,apellidoConcerje,pass);
        BaseDeDatos.agregarConcerje(concerje1);//una vez agregado al arreglo se puede volver a crear otroconcerje con esta variable
    }




    /**
     * Metodo que permite dar de alta a un usuario que por algún motivo ha sido dado de baja en algún momento, impidiéndosele
     * realizar operaciones en el sistema. A través de este método derivado de la interfaz IAbmUsuario, se reactiva en este caso
     * a un concerje.
     */
    @Override
    public void darDeAltaUsuario(String dni) {
        try{
            Concerje concerje = BaseDeDatos.obtenerUnConcerje(dni);
            if(!isEstadoDeAlta()) {
                concerje.setEstadoDeAlta(true);
            }
        }
        catch (UsuarioInexistenteException e){
            e.printStackTrace();
        }
    }
    /**
     * Método derivado de la interfaz IAbmUsuario. En el caso del administrador da de baja a un concerje por despido o por
     * causa de fallecimiento o renuncia.
     */
    @Override
    public void darDeBajaUsuario(String dni) {
        try {
            Concerje concerje = BaseDeDatos.obtenerUnConcerje(dni);
            if(isEstadoDeAlta()){
                concerje.setEstadoDeAlta(false);
            }
        }
        catch (UsuarioInexistenteException e){
            e.printStackTrace();
        }
    }
    /**
     * Método de la interfaz IAbmHabitacion utilizado en este caso para agregar habitaciones al sistema
     * que han sido construidas recientemente como causa de una ampliación física del hotel.
     */
    @Override
    public void darAltaHab(String numeroHab) {


        System.out.println("Ingrese la capacidad de la habitacion");
        byte capacity = LoginMenu.scanner.nextByte();
        System.out.println("Ingrese el tipo de habitacion (descripcion)");
        String type = LoginMenu.scanner.next();
        System.out.println("Ingrese precio diario para la habitacion");
        double price = LoginMenu.scanner.nextDouble();

        if(!BaseDeDatos.existeHabitacion(numeroHab)) {
            Habitacion habitacion = new Habitacion(numeroHab,capacity,type,price);
            BaseDeDatos.agregarHabitacion(habitacion);
        }
        else {
            System.out.println("Esa habitacion ya existe, la nueva no fue creada");
        }

    }
    /**
     * Método de la interfaz IAbmHabitacion que retira del sistema una habitación que por remodelación
     * ha sido destruida y ya no será más parte del sistema dado que ya no existe físicamente.
     */
    @Override
    public void darBajaHab(String numeroHab) {

        System.out.println("Ingrese numero de la habitacion a eliminar");
        String numero = LoginMenu.scanner.next();
        if(BaseDeDatos.existeHabitacion(numero)){
            BaseDeDatos.eliminarHabitacion(numero);
        }
        else {
            System.out.println("Esa habitacion no existe, no puede eliminarse");
        }
    }
    /**
     * JSON que devuelve en dicho formato amigo de todos los programadores, los datos pertenecientes al
     * administrador.
     */
    @Override
    public JSONObject getJson() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("nombre", getNombre());
        obj.put("apellido", getApellido());
        obj.put("dni", getDni());

        return obj;
    }
}