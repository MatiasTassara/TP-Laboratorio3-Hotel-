package clases;

import customExceptions.FaltaDisponibilidadException;
import customExceptions.HabitacionNulaException;
import customExceptions.IndiceIncorrectoException;
import customExceptions.PasajeroNullException;
import interfaces.IAbmHabitacion;
import interfaces.IAbmUsuario;
import net.time4j.PlainDate;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Esta clase contiene la información básica de un concerje de hotel, que forma parte de sus atributos,
 * su comportamiento y todas las tareas que realiza. Esta clase hereda de la clase Usuario que integra tanto
 * los atributos como los comportamientos comunes que hay entre los tres tipos de usuarios que tiene el
 * programa: Administrador / Conserje / Pasajero.
 * Además implementa interfaces importantes para el buen desempeño de su función de concerje.
 *
 *
 */
public class Concerje extends Usuario implements IAbmUsuario, IAbmHabitacion,Serializable {



    private String turno;
    /**
     * Constructor de Concerje que recibe los atributos por parámetro y que serán
     * llamados en la superclase Usuario.
     * @param dni - simplemente su numero de documento de tipo String
     * @param nombre
     * @param apellido
     */

    public Concerje(String dni, String nombre, String apellido,String password)

    {
        super(dni, nombre, apellido,password);
        this.turno = "Rotativo";

    }

    /**
     * toString nos devuelve en un String con los datos de los atributos para poder
     * realizar diferentes cosas, entre ellas, imprimir por pantalla en caso de que
     * se desee hacerlo. Para ello llamamos a la super clase Usuario.
     */

    @Override
    public String toString() {

        return super.toString();
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    /**
     * Método de la interfaz IAbmUsuario. Una de las responsabilidades
     * del concerje es dar de alta a los pasajeros para poder registrarlos en el sistema.
     */
    @Override
    public void crearUsuario() {

            String nombrePasajero = "";
            String apellidoPasajero = "";
            String dniPasajero = "";
            String celular = "";
            String eMail = "";
            String ciudadOrigen = "";
            String domicilio = "";
            char correcto = 'n';
            String pass = "";
            Pasajero pasajero1;
            int contador = 0;

            while(correcto == 'n' || correcto == 'N') {
                System.out.println("**Alta de nuevo Pasajero**");
                System.out.println("Ingrese nombre(s): ");
                nombrePasajero = LoginMenu.scanner.next();
                System.out.println("Ingrese Apellido(s)");
                apellidoPasajero = LoginMenu.scanner.next();
                System.out.println("Ingrese DNI");
                dniPasajero = LoginMenu.scanner.next();
                System.out.println("Ingrese numero de telefono");
                celular = LoginMenu.scanner.next();
                System.out.println("Ingrese e-mail");
                eMail = LoginMenu.scanner.next();
                System.out.println("Ingrese ciudad de origen");
                ciudadOrigen = LoginMenu.scanner.next();
                System.out.println("Ingrese domicilio");
                domicilio = LoginMenu.scanner.next();
                System.out.println("Ingrese password");
                pass = LoginMenu.scanner.next();
                System.out.println("Los datos ingresados son: \nNombre: " + nombrePasajero + "\nApellido: " + apellidoPasajero +
                        "\nDNI: " + dniPasajero + "\nCelular: " + celular + "\nE-Mail: " + eMail +
                        "Ciudad de origen: " + ciudadOrigen + "\nDomicilio: " + domicilio +
                        "\nEs correcta la informacion? oprimir 's' para guardar... 'n' para modificar... ");
                correcto = LoginMenu.scanner.next().charAt(0);
            }



            pasajero1 = new Pasajero(dniPasajero,nombrePasajero,apellidoPasajero,celular,eMail,ciudadOrigen,domicilio,pass);
            BaseDeDatos.agregarPasajero(pasajero1);//una vez agregado al arreglo se puede volver a crear otroconcerje con esta variable


        }





    /**
     * Método de la interfaz IAmbUsuario. Consiste en dar de baja a un pasajero en el sistema.
     *
     */
    @Override
    public void darDeBajaUsuario(String dni) {

        try {
            Pasajero pasajerito = BaseDeDatos.buscaPasajeroporDni(dni);
            pasajerito.ponerEnBaja();
            BaseDeDatos.agregarPasajero(pasajerito);

        }catch(PasajeroNullException p)
        {
            p.printStackTrace();;
        }catch(RuntimeException r)
        {
            r.printStackTrace();
        }


    }
    /**
     * Método de la interfaz IAmbUsuario que se utiliza cuando un pasajero fue anteriormente
     * dado de baja y se necesita darle el alta nuevamente en el sistema.
     */
    @Override
    public void darDeAltaUsuario(String dni) {

        try {
            Pasajero pasajerito = BaseDeDatos.buscaPasajeroporDni(dni);
            pasajerito.ponerEnAlta();
            BaseDeDatos.agregarPasajero(pasajerito);

        }catch(PasajeroNullException p)
        {
            p.printStackTrace();
        }catch(RuntimeException r)
        {
            r.printStackTrace();
        }
    }
    /**
     * Método que permite verificar si en vez de recibir un objeto de tipo Pasajero
     * recibimos un NULL. En ese caso lanza una excepción que será capturada en el
     * bloque invocante.
     * @param pasajerito - Es la Reserva que se envía para ser evaluada.
     */
    public void comprobarPasajero(Pasajero pasajerito)
    {
        if(pasajerito == null)
            throw new PasajeroNullException("El pasajero es inexistente");
    }
    /**
     * Método de la interfaz IAbmHabitacion. A traves del mismo, podemos habilitar
     * una determinada habitación cuyo número es pasado por parámetro y que
     * anteriormente fue deshabilitada por algun motivo que puede ser limpieza,
     * refacción, etc.
     */

    @Override
    public void darAltaHab(String numeroHab) {

        try {

            Habitacion hab = BaseDeDatos.buscarPorNumero(numeroHab);
            comprobarHabitacion(hab);
            int indice = BaseDeDatos.obtenerIndiceHabitacion(numeroHab);
            hab.habilitar();
            BaseDeDatos.agregarHabitacionAlIndice(hab, indice);
        }catch(HabitacionNulaException h)
        {
            h.printStackTrace();
        }catch(RuntimeException r)
        {
            r.printStackTrace();
        }

    }
    /**
     * Método perteneciente a la interfaz IAbmHabitacion. Permite deshabilitar una determinada
     * habitación por un motivo que puede ser variado.
     */
    @Override
    public void darBajaHab(String numeroHab) {
        try {

            Habitacion hab = BaseDeDatos.buscarPorNumero(numeroHab);
            comprobarHabitacion(hab);
            int indice = BaseDeDatos.obtenerIndiceHabitacion(numeroHab);
            hab.deshabilitar();
            BaseDeDatos.agregarHabitacionAlIndice(hab, indice);
        }catch(HabitacionNulaException h)
        {
            h.printStackTrace();
        }catch(RuntimeException r)
        {
            r.printStackTrace();
        }

    }
    /**
     * Cuando un pasajero desea realizar una reserva lo primero que se hace es consultar si
     * en los dias que el pasajero desea hay habitaciones disponibles para la cantidad de personas
     * que quiere hospedarse en el hotel. El método confecciona un arreglo dinámico con los números
     * de habitaciones más convenientes de acuerdo a las fechas y a la capacidad de las habitaciones.
     * Dicho arreglo será enviado al Método Reservar, que es el encargado de concretar e instanciar
     * la reserva.
     * @param fechaIngreso - a partir de qué fecha desean hospedarse
     * @param fechaEgreso - hasta qué fecha desean quedarse en el hotel
     * @param cantPasajeros - variable que nos permite calcular la cantidad de habitaciones y de qué tipo.
     * @return
     */

    ArrayList<String> asignarHabitaciones(PlainDate fechaIngreso, PlainDate fechaEgreso, int cantPasajeros)
    {
        ArrayList<String>numerosHabitaciones = new ArrayList<String>();
        String aux = "";
        try {
            verificarDisponibilidad(cantPasajeros);

            int i = 0;
            ArrayList<Habitacion> libres = BaseDeDatos.buscarAptas(fechaIngreso, fechaEgreso);
            Collections.sort(libres);
            Collections.reverse(libres);
            while(cantPasajeros > 0)
            {
                while(i < libres.size())
                {
                    if(cantPasajeros >= libres.get(i).getCapacidad())
                    {
                        cantPasajeros = cantPasajeros - libres.get(i).getCapacidad();
                        aux = libres.get(i).getNumHabitacion();
                        numerosHabitaciones.add(aux);

                    }
                    i++;
                }
                if(cantPasajeros > 0)
                {
                    i = libres.size()-1;
                    aux = libres.get(i).getNumHabitacion();
                    numerosHabitaciones.add(aux);


                    cantPasajeros = 0;
                }
            }



        }catch(FaltaDisponibilidadException e){

            e.printStackTrace();

        }catch(RuntimeException e) {

            e.printStackTrace();
        }finally {
            return numerosHabitaciones;
        }
    }
    /**
     * Método que pide información a Base de Datos acerca de la disponibilidad de espacio en el hotel
     * para contener la cantidad de pasajeros que desea hospedarse. En caso de no haber capacidad hotelera
     * arroja una excepción advirtiendo que esa operación no podrá ser realizada y la misma será capturada en
     * el bloque invocante.
     * @param cantPasajeros - cantidad de personas que desea hospedarse en el hotel.
     * @return - Devulve una variable booleana que permite evaluar en el método invocante.
     * @throws FaltaDisponibilidadException - Anuncio del tipo de excepción que se manejará.
     */
    public boolean verificarDisponibilidad(int cantPasajeros)throws FaltaDisponibilidadException
    {
        boolean verifica = BaseDeDatos.hayCapacidad(cantPasajeros);
        if (verifica  == false)
            throw new FaltaDisponibilidadException("No alcanza la capacidad del hotel para hospedar a los pasajeros");
        return verifica;

    }
    /**
     * En este método se concreta la reserva. Se instancia y se incorpora a la Base de Datos para que posteriormente
     * el administrador la guarde en un archivo y nos quede así la información almacenada de la misma.
     * @param dni - documento de la persona que hará la reserva
     * @param fechaIngreso - fecha en la cual comenzará
     * @param fechaEgreso - fecha de finalización
     * @param numerosHabitaciones - ArrayList con el numero de habitaciones que debe ocupar para ese rango de fechas.
     * @return
     */
    public void reservar(String dni, PlainDate fechaIngreso, PlainDate fechaEgreso, ArrayList<String> numerosHabitaciones)
    {
        int numeroReserva = BaseDeDatos.obtenerUltimaReserva() + 1;
        Reserva nuevaReserva = new Reserva(numeroReserva, dni, fechaIngreso, fechaEgreso, numerosHabitaciones);


        try {
            for(int i = 0; i < numerosHabitaciones.size(); i++)
            {

                Habitacion hab = BaseDeDatos.buscarPorNumero(numerosHabitaciones.get(i));
                comprobarHabitacion(hab);
                hab.ocupar(fechaIngreso, fechaEgreso);
                BaseDeDatos.agregarHabitacionAlIndice(hab, i);

            }

            Pasajero nuevoPasajero = BaseDeDatos.buscaPasajeroporDni(dni);
            nuevoPasajero.agregarReservaAlHistorial(nuevaReserva);
            BaseDeDatos.agregarPasajero(nuevoPasajero);
            BaseDeDatos.agregarReserva(nuevaReserva);
        }catch(HabitacionNulaException h)
        {
            h.printStackTrace();
        }catch(RuntimeException r)
        {
            r.printStackTrace();
        }



    }
    /**
     * Cuando el pasajero llega al hotel, realiza el Check in. El método comprueba si ese número de reserva
     * existe. Hace efectiva la reserva, atributo de la clase Reserva. Y luego la incorpora a Base de Datos.
     * @param numeroReserva - String con el que se envía la solicitud a Base de Datos para que se pueda
     * enviar y recibir la reserva.
     */
    public void realizarCheckIn(int numeroReserva)
    {
        try{
            Reserva reser = BaseDeDatos.obtenerReserva(numeroReserva);
            comprobarReserva(reser);
            int indice = BaseDeDatos.obtenerIndiceReserva(numeroReserva);
            comprobarIndice(indice);
            reser.hacerEfectiva();
            BaseDeDatos.agragarReservaAlIndice(indice, reser);
        }catch(ReservaNulaException r)
        {
            r.printStackTrace();
        }catch(IndiceIncorrectoException i)
        {
            i.printStackTrace();
        }

    }
    /**
     * Este método pide la reserva a Base de Datos, comprueba si existe. De se así, pide a Base de Datos
     * el índice de la colección donde se encuentra. utiliza el método cobrar y descuenta el importe ingresado
     * por pantalla. Si el importe es suficiente, la deuda se cancelará. Posteriormente, vuelve los Frigobares
     * de cada una de las habitaciones que conforman la reserva a cero. De esta manera otro pasajero no pagará
     * saldos que no le correspondan. Y posteriormente se vuelve a cargar en Base de Datos en la misma posición
     * donde se encontraba anteriormente.
     * @param numeroReserva
     * @param importe - double ingresado por el conserje por el importe percibido por parte del pasajero.
     */
    public void realizarChekOut(int numeroReserva, double importe) //importe que ingresa en dinero el cliente
    {

        try {
            Reserva reser = BaseDeDatos.obtenerReserva(numeroReserva);
            comprobarReserva(reser);
            int indice = BaseDeDatos.obtenerIndiceReserva(numeroReserva);
            comprobarIndice(indice);
            cobrar(reser, importe);
            ArrayList<String> habitaciones = reser.getNumerosHabitaciones();
            for(int i = 0; i < habitaciones.size(); i++)
            {
                Habitacion hab = BaseDeDatos.buscarPorNumero(habitaciones.get(i));
                comprobarHabitacion(hab);
                hab.getFrigobar().cancelarSaldo();
                int indHab = BaseDeDatos.obtenerIndiceHabitacion(habitaciones.get(i));
                comprobarIndice(indHab);
                BaseDeDatos.agregarHabitacionAlIndice(hab, indHab);
            }
        }catch(ReservaNulaException r)
        {
            r.printStackTrace();
        }catch(IndiceIncorrectoException i)
        {
            i.printStackTrace();
        }
        catch(HabitacionNulaException h)
        {
            h.printStackTrace();
        }catch(RuntimeException r)
        {
            r.printStackTrace();
        }

    }

    /**
     * Método utilizado por el método check out. Toma el importe aportado por el pasajero,
     * llama a métodos propios de la clase Reserva, calcula el monto adeudado y lo resta.
     * Si el importe fue suficiente confirma el pago total. Si el importe no alcanzó a cubrir
     * la deuda, quedará el registro en el historial de reserva de ese pasajero y no se confirmará
     * el pago total.
     * @param reserva
     * @param importe - importe ingresado por teclado. Tarea del concerje de turno.
     */
    public void cobrar(Reserva reserva, double importe) //recibimos el importe que paga y chekeamos si cancelamos
    {

        if(importe == reserva.calcularMonto())
        {
            reserva.confirmarPago();

        }else if(importe < reserva.calcularMonto())
        {
            reserva.descontarSaldo(importe);
        }


    }
    /**
     * Método que permite cancelar una reserva. Recorre un ArrayList de numeros de habitaciones
     * y va llamando a Base de Datos para poder acceder a cada habitación. A su vez, como siguiente
     * paso, desocupa y deja libre/s la/s correspondiente/s habitación /es para que otro pasajero
     * pueda reservar en esas fechas. Se realiza el tratamiento de excepciones.
     * @param numeroReserva
     */
    public void cancelarReserva(int numeroReserva)
    {

        try {
            Reserva reservita = BaseDeDatos.obtenerReserva(numeroReserva);
            comprobarReserva(reservita);
            ArrayList<String> habitaciones = reservita.getNumerosHabitaciones();

            for(int i = 0; i < habitaciones.size(); i ++)
            {
                Habitacion hab = BaseDeDatos.buscarPorNumero(habitaciones.get(i));
                comprobarHabitacion(hab);
                int indice = BaseDeDatos.obtenerIndiceHabitacion(habitaciones.get(i));
                comprobarIndice(indice);
                hab.desocupar(reservita.getFechaIngreso(), reservita.getFechaEgreso());
                BaseDeDatos.agregarHabitacionAlIndice(hab, indice);
            }
        }catch(ReservaNulaException r)
        {
            r.printStackTrace();
        }catch(HabitacionNulaException h)
        {
            h.printStackTrace();
        }catch(IndiceIncorrectoException i)
        {
            i.printStackTrace();
        }catch(RuntimeException r)
        {
            r.printStackTrace();
        }

    }

    /**
     * Método que lanza una excepción si la reserva es nula por algun error en
     * el ingreso de número de reserva. En caso de que no exista, avisa de esta
     * excepción para que sea capturada en el módulo invocante.
     * @param reservita - reserva que recibe para comprobar.
     */

    public void comprobarReserva(Reserva reservita)
    {
        if(reservita == null)
            throw new ReservaNulaException("No existe la reserva solicitada");
    }

    /**
     * Método que comprueba si una habitación devuelta por la Base de Datos es nula.
     * En ese caso lanza una excepción para que el módulo invocante lo capture.
     * @param hab - habitación que debe ser evaluada.
     */
    public void comprobarHabitacion(Habitacion hab)
    {
        if(hab == null)
            throw new HabitacionNulaException("Habitacion no existente. ");
    }
    /**
     * Método que comprueba si el índice devuelto desde Base de Datos es igual
     * a -1, en ese caso, no se debe continuar con la operación que se está realizando.
     * Para ello lanza la excepción que nos indica ese problema en particular
     * y que será capturada en el módulo invocante.
     * @param indice - índice que tenemos que evaluar y que utilizamos en otro método y que
     * nos señala una posición en un arreglo.
     */
    public void comprobarIndice(int indice)
    {
        if (indice == -1)
            throw new IndiceIncorrectoException("Indice incorrecto en la busqueda de habitacion o de reserva");
    }
    /**
     * Metodo que devuelve un ArrayList de Strings que puede ser impreso por pantalla. El mismo nos proporciona
     * los números de las habitaciones que en el momento de la consulta están aptas de ser reservadas u ocupadas.
     * @return - retorna el ArrayList de números de habitaciones en String.
     */
    public ArrayList<String> verDisponibles()
    {
        ArrayList<String> disponibles = BaseDeDatos.buscarNumerosDeDisponibles();
        return disponibles;
    }
    /**
     * Método que nos permite ver cuales son las habitaciones que no se pueden ocupar en ese momento dado de
     * la consulta.
     * @return - Devuelve un ArrayList de String con los números de las habitaciones.
     */
    public ArrayList<String> verOcupadas()
    {
        ArrayList<String> habitaciones = BaseDeDatos.buscarNumerosDeNoDisponibles();
        return habitaciones;
    }
    /**
     * Método que interactúa con el pasajero y que reune dos métodos muy importantes que podrían actuar de forma
     * independiente en caso de ser solicitado en otro formato de sistema. Los métodos más importantes llamados son
     * AsignarHabitaciones y reservar. En uno se elige la mejor opción para el caso que presenta el pasajero y en el
     * otro se genera la reserva, se genera el primer historial y se registra al pasajero en caso de que no esté registrado
     * anteriormente.
     */


    public void gestionarReserva(){

        System.out.println("Ingrese DNI del pasajero");
        String dniAux = LoginMenu.scanner.next();
        Pasajero auxPass = BaseDeDatos.buscaPasajeroporDni(dniAux);
        if(auxPass != null){
            System.out.println("Ingrese anio de llegada");
            int anyo = LoginMenu.scanner.nextInt();
            System.out.println("Ingrese mes de llegada");
            int mes = LoginMenu.scanner.nextInt();
            System.out.println("Ingrese dia de llegada");
            int dia = LoginMenu.scanner.nextInt();
            PlainDate aux = PlainDate.of(anyo,mes ,dia );

            System.out.println("Ingrese anio de salida");
            int anyosalida = LoginMenu.scanner.nextInt();
            System.out.println("Ingrese mes de salida");
            int mesSalida = LoginMenu.scanner.nextInt();
            System.out.println("Ingrese dia de salida");
            int diaSalida = LoginMenu.scanner.nextInt();
            PlainDate aux2 = PlainDate.of(anyosalida,mesSalida ,diaSalida);

            System.out.println("Ingrese cantidad de pasajeros");
            int cantPasajeros = LoginMenu.scanner.nextInt();
            ArrayList<String> arregloHabitaciones;
            try {
                arregloHabitaciones = asignarHabitaciones(aux, aux2, cantPasajeros);
                reservar(dniAux,aux ,aux2 ,arregloHabitaciones );
            }

            catch (FaltaDisponibilidadException e){
                e.printStackTrace();
            }
            catch (RuntimeException e){
                e.printStackTrace();
            }
        }
        else
            crearUsuario();
    }
    /**
     * Método que permite saber si para un determinado intervalo de fecha solicitado por el pasajero
     * se cuenta con capacidad hotelera y con disponibilidad de habitaciones con las combinaciones
     * necesarias según la cantidad de huespedes que se quieran hospedar.
     */
    public void consultarTelefonicamente(){

        System.out.println("Ingrese anio de llegada");
        int anyo = LoginMenu.scanner.nextInt();
        System.out.println("Ingrese mes de llegada");
        int mes = LoginMenu.scanner.nextInt();
        System.out.println("Ingrese dia de llegada");
        int dia = LoginMenu.scanner.nextInt();
        PlainDate aux = PlainDate.of(anyo,mes ,dia );

        System.out.println("Ingrese anio de salida");
        int anyosalida = LoginMenu.scanner.nextInt();
        System.out.println("Ingrese mes de salida");
        int mesSalida = LoginMenu.scanner.nextInt();
        System.out.println("Ingrese dia de salida");
        int diaSalida = LoginMenu.scanner.nextInt();
        PlainDate aux2 = PlainDate.of(anyosalida,mesSalida,diaSalida);
        ArrayList<Habitacion> libres = BaseDeDatos.buscarAptas(aux,aux2 );
        for(Habitacion habitacion: libres){
            System.out.println("------------------");
            habitacion.mostrarHabitacionPorPantalla();
            System.out.println("------------------");
        }
    }
    /**
     * Consulta a la base de datos el estado de una habitación y lo devuelve en forma de String
     * @param numeroDeHab Número de la habitación a consultar.
     * @return String con el estado de la habitación.
     */
    public String consultarHabitacion(String numeroDeHab) {
        Habitacion hab = BaseDeDatos.buscarPorNumero(numeroDeHab);
        if(hab != null) {
            return hab.mostrarHabitacion();
        }
        else return ("La habitación no fue encontrada");
    }
    /**
     * Método abstracto implementado de la clase abstracta usuario que devuelve una lista en formato JSON.
     * Dicha lista contiene los datos del concerje en cuestión.
     */
    @Override
    public JSONObject getJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("nombre", getNombre());
        obj.put("apellido", getApellido());
        obj.put("dni", getDni());
        obj.put("estadoDeAlta",isEstadoDeAlta() );
        obj.put("turno",getTurno() );
        return obj;


    }

}