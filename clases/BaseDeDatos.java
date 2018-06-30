package clases;


import net.time4j.PlainDate;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Clase que almacena toda la informacion acerca de los usuarios del sistema, habitaciones y reservas en distintas colecciones.
 * La funcionalidad de la clase es agregar, almacenar, buscar segun distintos parametros y retornar diferentes datos, segun lo soliciten
 * los usuarios.
 * La clase es final, su constructor privado y sus atributos y metodos son estaticos.
 * Al momento de comenzar el programa la informacion se levanta desde los archivos correspondientes.
 */
public final class BaseDeDatos implements Serializable{


    //CON EL CONSTRUCTOR PRIVADO, ESTO HACE QUE NO PUEDA SER INSTANCIADA

    private static ArrayList<Habitacion> habitaciones = new ArrayList<>();
    private static HashMap<String,Pasajero> pasajeros = new HashMap<>();
    private static ArrayList<Reserva> reservas = new ArrayList<>();
    private static HashMap<String,Concerje> concerjes = new HashMap<>();
    private static HashMap<String,Administrador> administradores = new HashMap<>();


    /**
     * Constructor de BaseDeDatos. Notese que es privado y por ende la clase no puede ser instanciada.
     */
  public BaseDeDatos(){
        habitaciones = new ArrayList<>();
        pasajeros = new HashMap<>();
        reservas = new ArrayList<>();
        concerjes = new HashMap<>();
        administradores = new HashMap<>();
    }

    /**
     * Lee un archivo "habitaciones.dat" y carga sus objetos - las habitaciones - en memoria, más específicamente a Base de datos.
     */


    public static void levantarAllCollections(){
        levantarPasajeros();
        levantarReservas();
        levantarConcerjes();
        levantarHabitaciones();
        levantarAdministradores();
    }
    /**
     * Extrae del archivo habitaciones.dat, en caso de existir tal archivo, los datos guardados y se los proporciona
     * en tiempo de ejecución al sistema para poder ser utilizados.
     */
    public static void levantarHabitaciones() {
        ObjectInputStream lectura = null;

        try {
            File file = new File("habitaciones.dat");
            if(file.exists()) {

                lectura = new ObjectInputStream(new FileInputStream(file));
                habitaciones = (ArrayList<Habitacion>) lectura.readObject();
            }
        }
        catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        finally {
            try {
                lectura.close();
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * Lee un archivo "pasajeros.dat" y carga sus objetos - los pasajeros - en memoria, más específicamente a Base de datos.
     */
    public static void levantarPasajeros() {
        ObjectInputStream lectura = null;

        try {
            File file = new File("pasajeros.dat");
            if(file.exists()) {

                lectura = new ObjectInputStream(new FileInputStream(file));
                pasajeros = (HashMap<String, Pasajero>) lectura.readObject();
            }
        }
        catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        finally {
            try {
                lectura.close();
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * Extrae del archivo concerjes.dat - en caso de existir - los datos contenidos para utilizarlos en Base de Datos
     * en tiempo de ejecución.
     */
    public static void levantarConcerjes() {
        ObjectInputStream lectura = null;

        try {
            File file = new File("concerjes.dat");
            if(file.exists()) {

                lectura = new ObjectInputStream(new FileInputStream(file));
                concerjes = (HashMap<String, Concerje>) lectura.readObject();
            }
        }
        catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        finally {
            try {
                lectura.close();
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * Lee un archivo "reservas.dat" y carga sus objetos - las reservas - en memoria, más específicamente a Base de datos.
     */
    public static void levantarReservas() {
        ObjectInputStream lectura = null;

        try {
            File file = new File("reservas.dat");
            if(file.exists()) {

                lectura = new ObjectInputStream(new FileInputStream(file));
                reservas = (ArrayList<Reserva>) lectura.readObject();
            }
        }
        catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        finally {
            try {
                lectura.close();
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * Lee del archivo administradores.dat - en caso de existir - los administradores que Base de datos
     * necesita manejar para que el sistema trabaje correctamente.
     */
    public static void levantarAdministradores() {
        ObjectInputStream lectura = null;
        File fail = new java.io.File("administradores.dat");
        if(!fail.isFile()){
            Administrador administrador1 = new Administrador("4321","Pepe","Sapo","1234");
            BaseDeDatos.agregarAdministrador(administrador1);
        }
        else {
            try {
                File file = new File("administradores.dat");
                if (file.exists()) {

                    lectura = new ObjectInputStream(new FileInputStream(file));
                    administradores = (HashMap<String, Administrador>) lectura.readObject();
                }
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (ClassNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            } finally {
                try {
                    lectura.close();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /////////// METODOS PARA HABITACIONES //////////////////////////////////////////////////////

    /**
     * Agrega un objeto de tipo Habitacion a BaseDeDatos.
     * @param habitacion objeto de tipo Habitacion.
     */
    public static void agregarHabitacion(Habitacion habitacion){
        habitaciones.add(habitacion);
    }

    public static void eliminarHabitacion(String numero){
        int index;
        for(int i = 0; i < habitaciones.size(); i++){
            if(habitaciones.get(i).getNumHabitacion().equals(numero)){
                index = i;
                habitaciones.remove(i);
                return;
            }
        }
    }

    /**
     * Busca y devuelve una habitación en la base de datos por número.
     * @param numHab Número de la habitación a buscar.
     * @return Objeto de tipo Habitacion.
     */
    public static Habitacion buscarPorNumero(String numHab){
        for(Habitacion habitacion: habitaciones){
            if(habitacion.getNumHabitacion().equals(numHab)){
                return  habitacion;
            }
        }
        return null;
    }
    /**
     * Produce una lista de todas las habitaciones del hotel
     * @return Lista en forma de ArrayList de objetos de tipo Habitacion.
     */
    public static ArrayList<Habitacion> obtenerHabitaciones(){
        return habitaciones;
    }

    /**
     * Produce una lista de las habitaciones que se encuentran disponibles para ser ocupadas en el presente momento.
     * @return Lista en forma de ArrayList de objetos de tipo Habitacion.
     */
    public static ArrayList<Habitacion> obtenerLibres(){   //devolver arraylist de string con las libres
        ArrayList<Habitacion> disponibles = new ArrayList<>();
        for(Habitacion habitacion: habitaciones){
            if(habitacion.isDisponible()){
                disponibles.add(habitacion);
            }
        }
        return disponibles;
    }
    /**
     * Realiza una recolección de todas las habitaciones de una determinada capacidad.
     * @param numero Cantidad de pasajeros que las habitaciones buscadas pueden albergar como máximo.
     * @return Lista en forma de ArrayList de objetos de tipo Habitacion.
     */
    public static ArrayList<Habitacion> buscarPorCapacidad(byte numero){
        ArrayList<Habitacion> capacidadBuscada = new ArrayList<>();
        for(Habitacion habitacion: habitaciones){
            if(habitacion.getCapacidad() == numero){
                capacidadBuscada.add(habitacion);
            }
        }
        return capacidadBuscada;
    }
    /**
     * Produce y devuelve una lista de todas las habitaciones de determinado tipo que la base de datos posee.
     * @param tipoHab Tipo de habitación de la cual se desea generar la lista.
     * @return Lista en forma de ArrayList de objetos de tipo Habitacion.
     */
    public static ArrayList<Habitacion> buscarPorTipo(String tipoHab){
        ArrayList<Habitacion> tipoBuscado = new ArrayList<>();
        for(Habitacion habitacion: habitaciones){
            if(habitacion.getTipo().equalsIgnoreCase(tipoHab)){
                tipoBuscado.add(habitacion);
            }
        }
        return tipoBuscado;
    }
    /**
     * Genera y devuelve una lista de las habitaciones cuyo precio por noche sea menor o igual a aquel recibido por parámetro.
     * @param precioMax Cota máxima de precio para realizar la lista.
     * @return Lista en forma de ArrayList de objetos de tipo Habitacion.
     */
    public static ArrayList<Habitacion> buscarPrecioMenorA(double precioMax){
        ArrayList<Habitacion> precioBuscado = new ArrayList<>();
        for(Habitacion habitacion: habitaciones){
            if(habitacion.getPrecioDiario() <= precioMax){
                precioBuscado.add(habitacion);
            }
        }
        return precioBuscado;
    }
    /**
     * El método recibe un numero de habitación y devuelve un boolean si existe o no tal habitación.
     * @param numero de habitación solicitado
     * @return
     */
    public static boolean existeHabitacion(String numero){
        boolean estaOno = false;
        for(int i = 0; i < habitaciones.size(); i++){
            if(habitaciones.get(i).getNumHabitacion().equals(numero)){
                estaOno = true;
            }
        }
        return estaOno;
    }
    /**
     * Produce una lista del estado actual de todas las habitaciones del hote.
     * @return Lista en forma de ArrayList de Strings del estado de cada habitacion.
     */
    public static ArrayList<String> listarHabitaciones(){
        ArrayList<String> listaHabitaciones = new ArrayList<>();
        for(Habitacion habitacion : habitaciones){
            listaHabitaciones.add(habitacion.mostrarHabitacion());
        }
        return listaHabitaciones;
    }
    /**
     * Elimina una habitación de la Base de datos a partir de su número de habitación.
     * @param numero Número en forma de String de la habitación a ser eliminada.
     */
    public static void quitarHabitacion(String numero){
        for(int i = 0; i < habitaciones.size(); i++){
            if(habitaciones.get(i).getNumHabitacion().equalsIgnoreCase(numero)){
                habitaciones.remove(habitaciones.get(i));
            }
            if(i == habitaciones.size()){
                //lanzar una excepcion custom cuando no encuentra la habitacion de ese numero
            }
        }
    }
    /**
     * Elimina una habitación de la Base de datos a partir de una instancia de Habitacion
     * @param habitacion Objeto de tipo Habitacion.
     */
    public static void quitarHabitacion(Habitacion habitacion){
        habitaciones.remove(habitacion);
    }
    /**
     * Devuelve la precio por noche de una habitación determinada a partir de su número de habitación.
     * @param numHabitacion Número en forma de String de la habitación para la cual se desea saber la tarifa.
     * @return Precio de la habitación por noche. Devuelve '-1' si la habitación no existe.
     */
    public static double obtenerTarifa(String numHabitacion) {
        for(int i = 0; i < habitaciones.size(); i++) {
            if(habitaciones.get(i).getNumHabitacion().equals(numHabitacion)) {
                return habitaciones.get(i).getPrecioDiario();
            }
        }
        return -1;
    }
    /**
     * Devuelve el saldo acumulado del frigobar de una determinada habitación para un momento dado.
     * @param numHabitacion Número de habitación para la cual se desea averiguar el saldo.
     * @return Saldo de la habitación. Devuelve '-1' si la habitación no existe.
     */
    public static double obtenerSaldoFrigobar(String numHabitacion) {
        for(int i = 0; i < habitaciones.size(); i++) {
            if(habitaciones.get(i).getNumHabitacion().equals(numHabitacion)) {
                return habitaciones.get(i).getFrigobar().getSaldo();
            }
        }
        return -1;
    }
    /**
     * Determina si el hotel en su totalidad posee la capacidad suficiente para albergar una determinada cantidad de huespedes.
     * @param cantPasajeros Cantidad de personas para la cual se desea saber si hay capacidad
     * @return TRUE si hay capacidad, FALSE si no la hay.
     */
    public static boolean hayCapacidad(int cantPasajeros) {
        int capacidadTotal = 0;
        for(int i = 0; i < habitaciones.size(); i++) {
            capacidadTotal += habitaciones.get(i).getCapacidad();
        }
        if(capacidadTotal > cantPasajeros) {
            return true;
        }
        return false;
    }
    /**
     * Produce una lista con las habitaciones disponibles para ser ocupadas dada una estadía.
     * @param ingreso Fecha del inicio de la estadía en formato PlainDate.
     * @param egreso Fecha del fin de la estadía en formato PlainDate.
     * @return Lista en formato ArrayList de objetos de tipo Habitacion.
     */
    public static ArrayList<Habitacion> buscarAptas(PlainDate ingreso, PlainDate egreso){
        ArrayList<Habitacion> aptas = new ArrayList<>();
        for(int i = 0; i < habitaciones.size(); i++) {
            if(habitaciones.get(i).isDisponible(ingreso,egreso)) {
                aptas.add(habitaciones.get(i));
            }
        }
        return aptas;
    }
    /**
     * Produce una lista con el número de todas las habitaciones disponibles para ser ocupadas en el momento actual.
     * @return Lista en forma de ArrayList de Strings.
     */
    public static ArrayList<String> buscarNumerosDeDisponibles(){
        ArrayList<String> lista = new ArrayList<>();
        for(int i = 0; i < habitaciones.size();i++){

            lista.add(habitaciones.get(i).getNumHabitacion());
        }

        return lista;
    }
    /**
     * Produce una lista con el número de todas las habitaciones que no están disponibles para ser ocupadas en el momento actual.
     * @return Lista en forma de ArrayList de Strings.
     */
    public static ArrayList<String> buscarNumerosDeNoDisponibles(){
        ArrayList<String> lista = new ArrayList<>();
        for(int i = 0; i < habitaciones.size(); i++){
            if(!habitaciones.get(i).isDisponible()){
                lista.add(habitaciones.get(i).getNumHabitacion());
            }
        }
        return lista;
    }
    /**
     * Devuelve el número del indice de la lista de habitaciones de Base de datos en donde se encuentra una determinada habitación.
     * Método util al ser usado de forma conjunta con agregarHabitacionAlIndice().
     * @param numeroHab Número de la habitación para la cual se desea saber el indice.
     * @return Indice en el ArrayList de habitaciones.
     */
    public static int obtenerIndiceHabitacion(String numeroHab){
        int index = -1;
        for(int i = 0; i < habitaciones.size(); i++){
            if(numeroHab.equals(habitaciones.get(i).getNumHabitacion())){
                index = i;
            }
        }
        return index;
    }
    /**
     * Agrega una habitación al ArrayList de habitaciones en Base de datos en un determinado indice.
     * Método util al ser usado de forma conjunta con obtenerIndiceHabitacion().
     * @param habitacion Objeto de tipo Habitacion a insertar.
     * @param indice Indice en el que se desea insertar la habitación,
     */
    public static void agregarHabitacionAlIndice(Habitacion habitacion,int indice){
        habitaciones.add(indice,habitacion );
    }
    /**
     * Este método se encuentra de forma opcional por si se desea en algún momento listar los productos que se encuentran
     * en el frigobar de una determinada habitación.
     * @return devuelve un listado de productos
     */
    public static String listarFrigobar(){
        StringBuffer stringBuffer = new StringBuffer();
        Frigobar frigobar = habitaciones.get(0).getFrigobar();
        HashMap<String,Double> frigo = frigobar.getProductos();
        Iterator iterator = frigo.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry passengers = (Map.Entry) iterator.next();
            stringBuffer.append("\nProducto: " + passengers.getKey() + " - Precio: $" +
                    String.valueOf(passengers.getValue()));
        }
        String listado = stringBuffer.toString();
        return listado;
    }
    /**
     * Método que permite tener acceso a la colección de habitaciones que componen la reserva de un
     * pasajero en particular
     * @param dni: pasajero
     * @return: colección de habitaciones de la reserva de tal pasajero del cual se ingresó el dni.
     */
    public static ArrayList<Habitacion> obtenerHabitacionesDeReserva(String dni){
        int ultima = obtenerNumeroDeUltimaReserva(dni);
        ArrayList<Habitacion> habReserva = new ArrayList<>();
        ArrayList<String> arreNumerosHabitacion = new ArrayList<>();
        for(int i = 0; i < reservas.size(); i++){
            if(ultima == reservas.get(i).getNumeroReserva()){
                arreNumerosHabitacion  = reservas.get(i).getNumerosHabitaciones();
            }
        }
        for(int i = 0; i < arreNumerosHabitacion.size(); i++){
            for(int j = 0; j < habitaciones.size(); j++){
                if(arreNumerosHabitacion.get(i).equals(habitaciones.get(j).getNumHabitacion())){
                    habReserva.add(habitaciones.get(j));
                }
            }
        }
        return habReserva;
    }


    ///////////         METODOS DE PASAJEROS       ////////////////////////////////////////
    /**
     * Agrega un pasajero a la Base de datos.
     * @param pasajero Objeto de tipo pasajero a agregar a la Base de datos.
     */
    public static void agregarPasajero(Pasajero pasajero){
        pasajeros.put(pasajero.getDni(),pasajero);

    }
    /**
     * @return Devuelve un HashMao con todos los pasajeros cargados en la Base de datos del hotel.
     */
    public static HashMap<String,Pasajero> obtenerPasajeros(){
        return pasajeros;
    }
    /**
     * Elimina un pasajero de la Base de datos de pasajeros a partir de su DNI.
     * @param numDoc Número de DNI en forma de String del pasajero a eliminar.
     */
    public static  void quitarPasajero(String numDoc){
        pasajeros.remove(numDoc);
    }
    /**
     * Busca y devuelve un pasajero en la Base de datos.
     * @param dniPasajero DNI del pasajero que se busqueda.
     * @return Objeto de tipo Pasajero. Devuelve null si no se lo encuentra.
     */
    public static Pasajero buscaPasajeroporDni(String dniPasajero){
        Pasajero buscado = pasajeros.get(dniPasajero);
        return buscado;
    }
    /**
     * Busca un pasajero en la Base de datos y retorna su nombre y apellido en forma de String.
     * @param dniPasajero DNI del pasajero del que se quiere saber el nombre y apellido.
     * @return Nombre y apellido del pasajero en forma de String.
     */
    public static String buscaNombreYapellido(String dniPasajero){
        String ApellidoYnombre = pasajeros.get(dniPasajero).getApellido() + " " +  pasajeros.get(dniPasajero).getNombre();
        return ApellidoYnombre;

    }
    /**
     * Busca un pasajero en la Base de datos mediante su DNI y devuelve su número de teléfono.
     * @param dniPasajero DNI del pasajero del cual se quiere saber el número de teléfono.
     * @return Número de teléfono en forma de String.
     */
    public static String buscaTelefonoPorDni(String dniPasajero){
        String telefono = pasajeros.get(dniPasajero).getTelefonoMovil();
        return telefono;
    }



//////////////        METODOS DE RESERVAS   ////////////////////////////

    /**
     * Agrega una reserva a la Base de datos.
     * @param elemento Objeto de tipo Reserva a agregar.
     */
    public static void agregarReserva(Reserva elemento) {
        reservas.add(elemento);
    }
    /**
     * Produce una lista de todas las reservas en la base de datos
     * @return Lista en forma de ArrayList de objetos de tipo Reserva.
     */
    public static ArrayList<Reserva> obtenerReservas(){
        return reservas;
    }
    /**
     * Devuelve una reserva a partir del número de reserva.
     * @param numero Número de la reserva a retornar.
     * @return Objeto de tipo Reserva.
     */
    public static Reserva obtenerReserva(int numero) {
        for(Reserva reserva: reservas){
            if(reserva.getNumeroReserva() == numero) {
                return reserva;
            }
        }
        return null;//capturar un nullpointerexception mas arriba, en el metodo que quiera modificar el retorno
        //o bien una excepcion custom lanzada desde este metodo
    }
    /**
     * Devuelve la última reserva que haya sido cargada a la base de datos.
     * @return Objeto de tipo Reserva.
     */
    public static int obtenerUltimaReserva() {
        if(reservas.size() == 0){
            return 0;
        }
        else {
            return reservas.get(reservas.size() - 1).getNumeroReserva();
        }
    }
    /**
     * Método que al recibir el dni de un pasajero devuelve un String con todo el detalle del historial
     * de reservas que tiene en su haber. De esta forma podemos visualizar si tiene alguna deuda o si
     * suele cancelar reservas.
     * @param dni del pasajero
     * @return string preparado para ser impreso por pantalla o recibido por algún dispositivo.
     */
    public static ArrayList<String> obtenerHistorialPrint(String dni) {
        ArrayList<Reserva> historialPasajero = pasajeros.get(dni).getHistorial();
        ArrayList<String> historialParaImprimir = new ArrayList<>();
        for(Reserva reserva : historialPasajero){
            historialParaImprimir.add(reserva.toString());
        }
        return historialParaImprimir;
    }
    /**
     * mediante este método ingresando el dni del pasajero podemos acceder al número
     * de la última reserva que el mismo realizó para poder consultar información o
     * ejecutar algún cobro o tipo de acción sobre la colección de habitaciones que
     * la compongan.
     * @param dni
     * @return número de la última reserva realizada.
     */
    public static int obtenerNumeroDeUltimaReserva(String dni){
        Pasajero pasajero = pasajeros.get(dni);
        ArrayList<Reserva> reser = pasajero.getHistorial();
        int index = reser.size() - 1;

        int numeroUltima = reser.get(index).getNumeroReserva();
        return numeroUltima;
    }
    /**
     * Método que nos permite conocer si el pasajero la última vez que visitó el hotel
     * quedó debiendo algún saldo.
     * @param dni
     * @return el último saldo que en caso de haber pagado todo será 0.
     */
    public static double obtenerSaldoUltimaReserva(String dni){
        Pasajero pasajero = pasajeros.get(dni);
        ArrayList<Reserva> reser = pasajero.getHistorial();
        int index = reser.size() - 1;
        double saldoUltimo = reser.get(index).getSaldo();
        return saldoUltimo;
    }


    /**
     * Devuelve a partir de un número de reserva el indice en el que se la encuentra en el ArrayList de reservas en la base de datos.
     * @param numeroReserva Número de reserva para la cual se desea saber el indice.
     * @return
     */
    public static int obtenerIndiceReserva(int numeroReserva){
        int index = -1;
        for(int i = 0; i < reservas.size(); i++){
            if(numeroReserva == reservas.get(i).getNumeroReserva()){
                index = i;
            }
        }
        return index;
    }
    /**
     * Coloca una reserva en un indice determinado en la base de datos
     * @param indice Indice en el que se desea colocar la reserva.
     * @param reserva Objeto de tipo reserva a colocar.
     */
    public static void agragarReservaAlIndice(int indice,Reserva reserva){
        reservas.add(indice,reserva );
    }


/////////// METODOS DE coNCERJES ////////////////////////

    /**
     * Agrega un concerje a la base de datos.
     * @param concerje
     */
    public static void agregarConcerje(Concerje concerje) {
        concerjes.put(concerje.getDni(),concerje);
    }
    /**
     * Devuelve un mapa con todos los concerjes cargados en la base de datos.
     * @return HashMap con las instancias de Concerje.
     */
    public static HashMap<String,Concerje> obtenerConcerjes(){
        return concerjes;
    }
    /**
     * Devuelve un concerje a partir de su DNI
     * @param dni DNI del concerje a obtener.
     * @return Objeto de tipo Concerje.
     */
    public static Concerje obtenerUnConcerje(String dni) {
        Concerje aux = concerjes.get(dni);
        return aux;
    }
///////////ADMINISTRADORES////////////////////////
    /**
     * Agrega un Administrador a la base de datos.
     * @param administrador Objeto de tipo Administrador a agregar.
     */
    public static void agregarAdministrador(Administrador administrador){
        //System.out.println(administrador.getDni());
        administradores.put(administrador.getDni(),administrador );
    }
    /**
     * Devuelve un mapa con todos los administradores cargados en la base de datos.
     * @return HashMap con las instancias de Administrador.
     */
    public static HashMap<String, Administrador> obtenerAdministradores() {
        return administradores;
    }
    /**
     * Devuelve un Administrador a partir de su DNI.
     * @param dni DNI del Administrador a devolver.
     * @return Objeto de tipo Administrador.
     */
    public static Administrador obtenerAdministrador(String dni){
        return administradores.get(dni);
    }

}