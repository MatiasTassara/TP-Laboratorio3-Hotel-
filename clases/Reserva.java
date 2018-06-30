package clases;

import net.time4j.PlainDate;
import net.time4j.range.DateInterval;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Reserva es la clase que nos permite instanciar reservas como su definición lo dice.
 * Esta clase permite que cada reserva tenga los datos más importantes del pasajero,
 * las fechas en las cuales se realizará y las habitaciones que ocupará. Además
 * nos permite conocer los importes generados a partir de la estadía.
 * Con lo cual, cada vez que instanciamos una reserva, accedemos a la posibilidad de acceder
 * a información muy valiosa para el hotel.
 *
 *
 */
public class Reserva implements Serializable {

    private int numeroReserva;
    private String dniPasajero;
    private PlainDate fechaIngreso;
    private PlainDate fechaEgreso;
    private ArrayList<String> numerosHabitaciones;
    private double saldo;
    private double monto;
    private boolean seHizoEfectiva;
    private int cantidadDias;
    private boolean pagoRealizado;
    /**
     * Constructor de Reserva. Recibe los datos más importantes que necesitamos.
     * @param numeroReserva - Un número de reservas generado incrementalmente, de forma de llevar un orden en la Base De Datos.
     * @param dni - Documento del pasajero que lo distingue del resto de los pasajeros del hotel.
     * @param fechaIngreso - Fecha en la cual hará su check in.
     * @param fechaEgreso - Fecha de Check out
     * @param numerosHabitaciones - ArrayList de String con los números de habitaciones que debemos reservar y manejar. Y a su ves sobre
     * las cuales realizaremos recorridos y operaciones a lo largo de todo el sistema.
     */
    public Reserva(int numeroReserva, String dni, PlainDate fechaIngreso, PlainDate fechaEgreso, ArrayList<String> numerosHabitaciones)
    {
        this.numeroReserva = numeroReserva;
        this.dniPasajero = dniPasajero;
        this.fechaIngreso = fechaIngreso;
        this.fechaEgreso = fechaEgreso;
        this.numerosHabitaciones = numerosHabitaciones;
        saldo = 0;
        monto = 0;
        seHizoEfectiva = false;
        pagoRealizado = false;
        cantidadDias = obtenerCantidadDeDias(fechaIngreso,fechaEgreso);
    }
    /**
     *
     * @return - devuelve un entero con el número de reserva. Es int porque lo hacemos incremental, es decir que sumamos.
     */
    public int getNumeroReserva()
    {
        return numeroReserva;
    }
    /**
     * Carga por parámetro el documento del pasajero previamente registrado.
     * @param dniPasajero - Recibe un String porque no es un dato sobre el que realizamos operaciones matemáticas.
     */
    public void setDni(String dniPasajero)
    {
        this.dniPasajero = dniPasajero;
    }
    /**
     *
     * @return - retorna el Documento del pasajero que se está hospedando o se va a hospedar.
     */
    public String getDniPasajero()
    {
        return dniPasajero;
    }
    /**
     * Nos regresa la fecha de ingreso, a partir de la cual la o las habitaciones se ocuparán.
     * @return PlainDate es un tipo de la librería time4j.
     */
    public PlainDate getFechaIngreso()
    {
        return fechaIngreso;
    }
    /**
     * Nos regresa la fecha de egreso, a partir de la cual la o las habitaciones se desocuparan.
     * @return - retirna un PlainDate de la librería time4j.
     */
    public PlainDate getFechaEgreso()
    {
        return fechaEgreso;
    }
    /**
     *
     * @return - Retorna un ArrayList con los números de las habitaciones que forman parte de la reserva.
     */
    public ArrayList<String> getNumerosHabitaciones()
    {
        return numerosHabitaciones;
    }
    /**
     * Permite ingresar el saldo en tipo double. Es un importe que refleja si queda algun saldo a pagar.
     * @param saldo
     */
    public void setSaldo(double saldo)
    {
        this.saldo = saldo;
    }
    /**
     *
     * @return - retorna el saldo de la reserva.
     */
    public double getSaldo()
    {
        return saldo;
    }
    /**
     * Monto representa el monto total a pagar. Ese monto reune, la suma de las habitaciones por los dias mas los gastos de
     * frigobar de cada una de las habitaciones que componen la reserva.
     * @param monto
     */
    public void setMonto(double monto)
    {

        this.monto = monto;
    }
    /**
     *
     * @return - Nos retorna el monto a pagar, permitiéndonos conocer cuál es la suma de todo el gasto total del pasajero.
     * Y que debe abonar necesariamente cuando realice el check out.
     */
    public double getMonto()
    {
        return monto;
    }
    /**
     * Este método sólo es llamado si el pasajero realizó el check in e ingresó al hotel.
     * Podremos tener un registro en caso de que no haya cumplido con la reserva pautada.
     */
    public void hacerEfectiva()
    {
        if (!seHizoEfectiva)
            seHizoEfectiva = true;

    }
    /**
     * Método que nos informa si se hizo o no efectiva la reserva.
     * @return - retorna un boolean, true si se efectivizó y false para el caso contrario.
     */

    public boolean getSeHizoEfectiva()
    {
        return seHizoEfectiva;
    }

    /**
     * Para que el sistema no imprima por pantalla terminología muy de la jerga de la programación,
     * el siguiente método construye un String con la respuesta del método getSeHizoEfectiva.
     * @return - Retorna String para imprimir o simplemente guardar pero con la información clara y no
     * un true o false sueltos sin saber en la suma de funcionalidades a qué se refiere.
     */

    public String mostrarEfectiva()
    {
        String respuesta;
        if (seHizoEfectiva == true)
            respuesta = "Reserva efectiva";
        else
            respuesta = "Reserva no efectiva";
        return respuesta;
    }
    /**
     * Método que retorna en un String toda la información más importante de la reserva.
     */
    @Override
    public String toString() {

        return "\nNumero de reserva: " + numeroReserva + "\nDni pasajero: " + dniPasajero + "\nFecha Ingreso: " + fechaIngreso
                + "\nFecha Egreso: " + fechaEgreso + "\nSaldo: " + saldo + "\nMonto: " + monto + mostrarEfectiva();
    }
    /**
     * Método que recorre el arreglo de  numeros de habitaciones y de cada una obtiene la tarifa. Utiliza
     * métodos de la Base de Datos. También junta información de lo gastado en los frigobares.
     *
     * @return - retorna la suma total de todos los días con los precios de cada habitación y lo consumido
     * en los frigobares.
     */
    public double calcularMonto()
    {

        double tarifa = 0;
        double frigo = 0;

        for(int i = 0; i < numerosHabitaciones.size(); i++)
        {

            tarifa += BaseDeDatos.obtenerTarifa(numerosHabitaciones.get(i));
            frigo += BaseDeDatos.obtenerSaldoFrigobar(numerosHabitaciones.get(i));
        }
        monto = tarifa * cantidadDias;
        monto += frigo;

        return monto;

    }
    /**
     * Metodo que permite recibir un importe por adelantado e ir descontandolo del monto de la reserva.
     * @param importe - importe ingresado por el conserje y proporcionado por el pasajero.
     */
    public void descontarSaldo(double importe)
    {
        saldo = monto - importe;
    }
    /**
     * Este método es muy importante porque es el que nos permite registrar si cuando el cliente deja el hotel
     * realizó el pago total de su cuenta, es decir de su reserva. De no se así quedará registrado el monto
     * adeudado que a futuro deberá pagar. De hecho queda el registro en el historial del pasajero.
     */

    public void confirmarPago()
    {
        pagoRealizado = true;
        saldo = 0;
    }
    /**
     * Método que permite conocer el rango de días de la reserva según las fechas ingresadas. Esto permite realizar
     * cálculos como por ejemplo el de monto.
     * @param inicio - fecha de ingreso
     * @param fin - fecha de egreso
     * @return int con la cantidad de días de ese intervalo de tiempo. Se utilizan métodos de la librería time4j.
     */
    public int obtenerCantidadDeDias(PlainDate inicio, PlainDate fin) { // Calcula la cantidad de días en un intervalo tomando un día menos, es decir de lunes a miercoles va a contar dos dias (que son los que se cobran en un hotel)
        DateInterval intervalo = DateInterval.between(inicio, fin);
        int cantidadDeDias = (int)intervalo.getLengthInDays()-1; //resta 1 porque cuando entra un dia y se va al otro cuenta como un solo día
        return cantidadDeDias;
    }




}