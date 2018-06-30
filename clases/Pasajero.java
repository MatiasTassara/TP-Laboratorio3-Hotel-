package clases;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Esta clase nos permite instanciar Pasajeros, con todos sus datos de procedencia. Además nos permite crear un
 * historial con todas las visitas que ha realizado al hotel. Contiene un ArrayList de Reserva y todo el detalle
 * preciso que se necesita para tener un buen dominio de la información.
 */

public class Pasajero extends Usuario implements Serializable{

    private String telefonoMovil;
    private String email;
    private String ciudadDeOrigen;
    private String domicilioOrigen;
    private ArrayList<Reserva>historial;
    private boolean checkIn;
    /**
     * Constructor vacio para utilizarlo en caso de ser necesario. En realidad está hecho siguiendo la
     * política de mantener nosotros el control del sistema y no derivar a la clase Object las responsabilidades
     * pertinentes a nuestro trabajo.
     */
    public Pasajero()
    {
        super();
        telefonoMovil =" ";
        email = " ";
        ciudadDeOrigen = " ";
        domicilioOrigen = " ";
        historial = new ArrayList<Reserva>();
        checkIn = false;
    }

    /**
     * Contructor que recibe todos los datos que corresponden a la creación de un pasajero.
     * @param dni - String porque no realizamos operaciones matemáticas con él.
     * @param nombre -
     * @param apellido -
     * @param telefonoMovil - String porque no realizamos operaciones matemáticas con él.
     * @param email - dato de contacto con el pasajero.
     * @param ciudadDeOrigen - ciudad de donde proviene.
     * @param domicilioOrigen - domicilio donde vive.
     */
    public Pasajero(String dni, String nombre, String apellido, String telefonoMovil, String email, String ciudadDeOrigen, String domicilioOrigen,String password)
    {
        super(dni, nombre, apellido,password);
        this.telefonoMovil = telefonoMovil;
        this.email= email;
        this.ciudadDeOrigen = ciudadDeOrigen;
        this.domicilioOrigen = domicilioOrigen;
        historial = new ArrayList<Reserva>();
        checkIn = false;
    }


    /**
     * En caso de utilizar un constructor vacio podemos cargar por parámetro el telefono
     * del pasajero.
     * @param telefonoMovil
     */

    public void setTelefonoMovil(String telefonoMovil)
    {
        this.telefonoMovil = telefonoMovil;
    }
    /**
     * En caso de necesitar el telefono del pasajero podemos recurrir a este método.
     * @return - retorna un String con el teléfono.
     */
    public String getTelefonoMovil()
    {
        return telefonoMovil;
    }
    /**
     * En el caso de haber utilizado el constructor vacío y necesitemos cargar a posteriori el email
     * o en caso de que el pasajero lo haya cambiado, a través de este método que recibe un String con
     * el email podemos volver a cargarlo.
     * @param email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }
    /**
     * Si algun administrador o el conserje necesita contactarse por email con el pasajero, a través de
     * este método puede acceder al email. Puede cumplir tareas importantes al momento de realizar la
     * reserva on line.
     * @return
     */
    public String getEmail()
    {
        return email;
    }
    /**
     * En caso de haber utilizado el constructor vacío podemos ingresar de forma independiente la ciudad de
     * origen del pasajero. Lo mismo para el caso de que el mismo haya cambiado de ciudad y tengamos que
     * actualizar sus datos.
     * @param ciudadDeOrigen - recibimos un String con la ciudad de donde proviene.
     */
    public void setciudadDeOrigen(String ciudadDeOrigen)
    {
        this.ciudadDeOrigen = ciudadDeOrigen;
    }
    /**
     * Este metodo nos permite acceder a la ciudad de origen del pasajero.
     * @return - Un String con la ciudad que tenemos registrada.
     */
    public String getCiudadDeOrigen()
    {
        return ciudadDeOrigen;
    }
    /**
     * Metodo para ingresar de manera independiente el domicilio previendo que el pasajero pudo haberse mudado
     * o simplemente hemos utilizado un constructor vacío.
     * @param domicilioOrigen - Recibe un String con lel domicilio de origen que debemos registrar.
     */
    public void setDomicilioDeOrigen(String domicilioOrigen)
    {
        this.domicilioOrigen = domicilioOrigen;
    }
    /**
     * Si necesitamos el domicilio del pasajero este método es el correcto para poder acceder a ese
     * atributo privado.
     * @return - Un String con el domicilio pertinente.
     */
    public String getDomicilioOrigen()
    {
        return domicilioOrigen;
    }
    /**
     * Este método permite tener acceso mediante un ArrayList conformado por Reservas al historial del
     * pasajero en nuestro hotel y de esta manera poder realizar tareas de estadística o simplemente
     * averiguar si ha cumplido con las reservas pautadas y si tiene algun saldo que esta adeudando
     * pagar al hotel.
     * @return - Un ArrayList de tipo Reserva.
     */
    public ArrayList<Reserva> getHistorial()
    {
        return historial;
    }
    /**
     *
     * @return - retorna un boolean en caso de que el pasajero haya realizado el correspondiente chek in
     */

    public boolean getChekIn()
    {
        return checkIn;
    }
    /**
     * Es una forma elegante de retornar el booleano convertido en String con el estado de Check in
     * @return - Un String que responde si el pasajero realizó el check in.
     */

    public String obtenerEstadoCheckIn()
    {
        String respuesta;
        if (checkIn == true)
            respuesta = "Check In realizado";
        else
            respuesta = "Check In sin realizar";
        return respuesta;


    }

    /**
     * Metodo que retorna en un String los datos pertinentes al pasajero.
     */
    @Override
    public String toString() {

        return super.toString() + "\nTelefono: " + telefonoMovil + "\nE-mail: " + email + "\nCiudad de Origen: " + ciudadDeOrigen +
                "\nDomicilio de Origen: " + domicilioOrigen + "\nEstado de Check In: " + obtenerEstadoCheckIn();
    }

    /**
     * Metodo que nos retorna el historial del pasajero con todas sus reservas realizadas a lo largo del tiempo.
     * @return - retorna un String.
     */
    String obtenerHistorial()
    {
        int longitud = historial.size();
        StringBuffer historia = new StringBuffer();
        for(int i = 0; i < longitud; i++)
        {
            historia.append(historial.get(i).toString());
        }
        String historialPasajero =  historia.toString();


        return historialPasajero;

    }

    /**
     * Metodo que recibe una reserva y la agrega al historia una vez que la misma SE HIZO EFECTIVA, metodo
     * que se encuentra en Reserva y que es llamado por conserje
     *
     * @param reser
     */
    public void agregarReservaAlHistorial(Reserva reser)
    {

        historial.add(reser);


    }
    /**
     * Implementacion del método abstracto de la clase abstracta usuario que devuelve en formato JSON
     * los datos del pasajero en cuestion.
     */
    public JSONObject getJson() throws JSONException
    {
        JSONObject obj = new JSONObject();
        obj.put("nombre", getNombre());
        obj.put("apellido", getApellido());
        obj.put("dni", getDni());
        obj.put("telefonoMovil", getTelefonoMovil());
        obj.put("email", getEmail());
        obj.put("ciudadDeOrigen", getCiudadDeOrigen());
        obj.put("domicilioOrigen", getDomicilioOrigen());

        return obj;


    }



}