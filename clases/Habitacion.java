package clases;

import net.time4j.*;

import net.time4j.range.ChronoInterval;
import net.time4j.range.DateInterval;
import net.time4j.range.IntervalCollection;
import net.time4j.range.ValueInterval;
import net.time4j.CalendarUnit;

import java.util.*;
import java.io.*;

//////////////////////////////////////////////////////////////////////////
/*  Se toma como "Disponible" a la ausencia de intervalo en el periodo  */
//////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////
/*                 "Disponible" es equivalente a "ocupable"             */
//////////////////////////////////////////////////////////////////////////


/**
 * Esta clase define los comportamientos básicos de la representación de una habitación de hotel promedio.
 * Cabe notar que el objeto generado por esta clase posee otro de la clase Frigobar y que se utilizan librerías externas para el manejo de fechas e intervalos temporales.
 *
 * @param numHabitacion Es el número de la habitación dentro del hotel, usualmente de 3 ó 4 cifras donde las dós ultimas determinan el número de habitación en el piso y la o las primeras el número de piso.
 * @param capacidad Determina la cantidad máxima de personas que la habitación puede albergar.
 * @param tipo Descripción del tipo de habitación que se trata.
 * @param precioDiario Es el costo diario por alquilar la habitación.
 * @param frigobar Es un objeto de la clase frigobar que representa al frigobar que cada habitación posee.
 * @param ColecIntervalosDeFechas Es una colección que posee intervalos de fechas en los que la habitación por alguna razón no se encuentra disponible para ser ocupada por un huesped.
 *
 */

@SuppressWarnings("unused")
public class Habitacion implements Comparable<Habitacion>,Serializable{

    private String numHabitacion;
    private byte capacidad;
    private String tipo;
    private double precioDiario;
    private Frigobar frigobar;
    private IntervalCollection<PlainDate> ColecIntervalosDeFechas;

    /**
     * @param numHabitacion Es el número de la habitación dentro del hotel, usualmente de 3 ó 4 cifras donde las dós ultimas determinan el número de habitación en el piso y la o las primeras el número de piso.
     * @param capacidad Determina la cantidad máxima de personas que la habitación puede albergar.
     * @param tipo Descripción del tipo de habitación que se trata, puede expresar el nivel de comfort de la misma.
     * @param precioDiario Es el costo diario por alquilar la habitación.
     * @return Objeto de tipo Habitacion.
     */
    public Habitacion(String numHabitacion,byte capacidad,String tipo,double precioDiario){
        this.numHabitacion = numHabitacion;
        this.capacidad = capacidad;
        this.tipo = tipo;
        this.precioDiario = precioDiario;
        frigobar = new Frigobar(); //ver el tema de parametros
        ColecIntervalosDeFechas = IntervalCollection.onDateAxis();
    }
    /**
     * @param capacidad Determina la cantidad máxima de personas que la habitación puede albergar.
     */
    public void setCapacidad(byte capacidad) {

        this.capacidad = capacidad;
    }
    /**
     * @param tipo Descripción del tipo de habitación que se trata.
     */
    public void setTipo(String tipo) {

        this.tipo = tipo;
    }
    /**
     * @param precioDiario Es el costo diario por alquilar la habitación.
     */
    public void setPrecioDiario(double precioDiario) {

        this.precioDiario = precioDiario;
    }
    /**
     * @return El número de la habitación dentro del hotel, usualmente de 3 ó 4 cifras donde las dós ultimas determinan el número de habitación en el piso y la o las primeras el número de piso.
     */
    public String getNumHabitacion() {

        return numHabitacion;
    }
    /**
     * @return La cantidad máxima de personas que la habitación puede albergar.
     */
    public byte getCapacidad() {

        return capacidad;
    }
    /**
     * @return Descripción del tipo de habitación que se trata. Puede expresar el nivel de comfort de la misma.
     */
    public String getTipo() {

        return tipo;
    }
    /**
     * @return Costo diario por alquilar la habitación.
     */
    public double getPrecioDiario() {

        return precioDiario;
    }
    /**
     * @return Objeto de tipo Frigobar.
     */
    public Frigobar getFrigobar(){
        return frigobar;
    }
    /**
     * @return Estado de la habitación para la fecha actual.
     */
    public String getEstado() {
        return getEstado(PlainDate.nowInSystemTime());
    }
    /**
     * @param fecha Fecha para la cual se desea saber el estado
     * @return Estado de la habitación para la fecha ingresada.
     */
    public String getEstado(PlainDate fecha) {
        if(!isDisponible(fecha)) {  // Si la coleccion no tiene intervalo con esa fecha implica disponibilidad, sino se itera la coleccion hasta dar con el intervalo que posea la fecha recibida por parametro
            Iterator<ChronoInterval<PlainDate>> iterador = ColecIntervalosDeFechas.iterator();
            while(iterador.hasNext()) {
                @SuppressWarnings("unchecked")
                ValueInterval<PlainDate, DateInterval, String> intervaloCasteado = (ValueInterval<PlainDate, DateInterval, String>)iterador.next();
                DateInterval intervaloCasteadoAgain = intervaloCasteado.getBoundaries();
                if(intervaloCasteadoAgain.contains(fecha)) {
                    return intervaloCasteado.getValue();
                }
            }
            System.out.println("Acá no debería llegar nunca, si llega es que hay algún problema en getEstado(fecha) de la clase Habitación");
        }
        return "Disponible";
    }

    /**
     * Cambia el estado en el que la habitación se encuentra el día actual, este es la razón por la cual la habitación no se encuentra libre.
     * Si se quiere utilizar un estado predefinido se debe utilizar el método deshabilitar().
     * @param estado Descripción del estado en el que se encuentra la habitación hoy.
     */
    public void setEstado(String estado) {
        setEstado(PlainDate.nowInSystemTime(), PlainDate.nowInSystemTime(), estado);
    }
    /**
     * Cambia el estado en el que la habitación se encuentra en determinada fecha, este es la razón por la cual la habitación no se encuentra libre.
     * ADVERTENCIA: Con este método se "pisa" el estado anterior, debe realizarse un chequeo previo si dada la lógica del programa este fuera un caso factible.
     * @param fecha Fecha para la cual se desea modificar el estado.
     * @param estado Descripción del estado en el que se encuentra la habitación en la fecha.
     */
    public void setEstado(PlainDate fecha, String estado) {
        setEstado(fecha, fecha, estado);
    }
    /**
     * Cambia el estado en el que la habitación se encuentra en un determinado período, este (el estado) es la razón por la cual la habitación no se encuentra libre.
     * ADVERTENCIA: Con este método se pueden "pisar" otros intervalos inadvertidamente, debe realizarse un chequeo previo si dada la lógica del programa este fuera un caso factible.
     * @param inicio Fecha de inicio del intervalo para el cual aplicará el estado.
     * @param fin Fecha de finalización del intervalo para el cual aplicará el estado.
     * @param estado Descripción del estado en el que se encuentra la habitación en el período.
     */
    public void setEstado(PlainDate inicio, PlainDate fin, String estado) {
        DateInterval intervaloSinValor = DateInterval.between(inicio, fin);
        ColecIntervalosDeFechas = ColecIntervalosDeFechas.minus(intervaloSinValor); // Primero se deberá eliminar el estado anterior para no queden los dos guardados..ni que me hubiera llevado un par de dias darme cuenta de esto..no..eso definitivamente nunca ocurrió
        ValueInterval<PlainDate, DateInterval, String> intervalo = intervaloSinValor.withValue(estado);
        ColecIntervalosDeFechas = ColecIntervalosDeFechas.plus(intervalo);
    }
    /**
     * Deshabilita una habitación el día de la fecha pasando su estado a ser el de "Deshabilitada"
     */
    public void deshabilitar() {
        char razon = 'z';
        deshabilitar(razon);
    }
    /**
     * Deshabilita una habitación el día de la fecha actual pasando su estado a ser el determinado por la razón ingresada.
     * @param razon Razón por la cual la habitación se está deshabilitando.  L-Limpieza D-Desinfeccion R-Reparación
     * 	 Nota: 	Cualquier otro caracter pondrá a la habitación en "Deshabilitada".
     * 			Si desea especificar algún caso en particular no contemplado por este método utilize setEstado().
     */
    public void deshabilitar(char razon) {
        switch(razon) {
            case 'r' :
            case 'R' : setEstado("Deshabilitada por Reparación");
                break;
            case 'd' :
            case 'D' : setEstado("Deshabilitada por Desinfección");
                break;
            case 'l' :
            case 'L' : setEstado("Deshabilitada por Limpieza");
                break;
            default: setEstado("Deshabilitada");
        }
    }
    /**
     * Deshabilita una habitación el día de la fecha recibida por parámetro pasando su estado a ser el de "Deshabilitada".
     * @param fecha Fecha para la cual se desea deshabilitar la habitación.
     */
    public void deshabilitar(PlainDate fecha) {
        setEstado("Deshabilitada");
    }
    /**
     * Deshabilita una habitación el día de la fecha recibida por parámetro pasando su estado a ser el determinado por la razón ingresada.
     * @param fecha Fecha para la cual se desea deshabilitar la habitación.
     * @param razon Razón por la cual la habitación se está deshabilitando.  L-Limpieza D-Desinfeccion R-Reparación
     * 	 Nota: 	Cualquier otro caracter pondrá a la habitación en "Deshabilitada".
     * 			Si desea especificar algún caso en particular no contemplado por este método utilize setEstado().
     */
    public void deshabilitar(PlainDate fecha, char razon) {
        switch(razon) {
            case 'r' :
            case 'R' : setEstado(fecha,"Deshabilitada: Reparación");
                break;
            case 'd' :
            case 'D' : setEstado(fecha,"Deshabilitada: Desinfección");
                break;
            case 'l' :
            case 'L' : setEstado(fecha,"Deshabilitada: Limpieza");
                break;
            default: setEstado(fecha,"Deshabilitada");
        }
    }
    /**
     * Deshabilita una habitación en el intervalo entre las fechas recibidas por parámetro pasando su estado a ser el de "Deshabilitada".
     * @param inicio Fecha desde la cual se dehabilitará la habitación.
     * @param fin Fecha hasta la cual se dehabilitará la habitación.
     */
    public void deshabilitar(PlainDate inicio, PlainDate fin) {
        setEstado(inicio,fin,"Deshabilitada");
    }
    /**
     * Deshabilita una habitación en el intervalo entre las fechas recibidas por parámetro pasando su estado a ser el determinado por la razón ingresada.
     * @param inicio Fecha desde la cual se dehabilitará la habitación.
     * @param fin Fecha hasta la cual se dehabilitará la habitación.
     * @param razon Razón por la cual la habitación se está deshabilitando.  L-Limpieza D-Desinfeccion R-Reparación
     * 	 Nota: 	Cualquier otro caracter pondrá a la habitación en "Deshabilitada".
     * 			Si desea especificar algún caso en particular no contemplado por este método utilize setEstado().
     */
    public void deshabilitar(PlainDate inicio, PlainDate fin, char razon) {
        switch(razon) {
            case 'r' :
            case 'R' : setEstado(inicio,fin,"Deshabilitada: ReparaciÃ³n");
                break;
            case 'd' :
            case 'D' : setEstado(inicio,fin,"Deshabilitada: DesinfecciÃ³n");
                break;
            case 'l' :
            case 'L' : setEstado(inicio,fin,"Deshabilitada: Limpieza");
                break;
            default: setEstado(inicio,fin,"Deshabilitada");
        }
    }
    /**
     * Deshabilita una habitación de manera indefinida a partir de la fecha recibida por parámetro pasando su estado a ser el determinado por la razón ingresada.
     * ADVERTENCIA: Este método "Pisará" cualquier intervalo posterior a la fecha recibida quedando a cargo de quien decida usar el método la responsabilidad de chequear que esto no produzca una pérdida de informacion ni atente contra el buen funcionamiento del programa.
     * @param fechaAPartirDeLaCual Fecha a partir de la cual desea deshabilitar la habitación.
     * @param razon Razón por la cual la habitación se está deshabilitando.  L-Limpieza D-Desinfeccion R-Reparación
     * 	 Nota: 	Cualquier otro caracter pondrá a la habitación en "Deshabilitada".
     * 			Si desea especificar algún caso en particular no contemplado por este método utilize setEstado().
     */
    public void deshabilitarIndefinidamente(PlainDate fechaAPartirDeLaCual,char razon){
        DateInterval intervaloSinValor = DateInterval.since(fechaAPartirDeLaCual); // Crea un intervalo comÃºn "Sin valor"
        ValueInterval<PlainDate, DateInterval, String> intervalo;	// Crea un intervalo con valor pero no lo inicia
        switch(razon) {	// Inicia el intervalo con valor usando el intervalo sin valor + el valor del String segun el case (razÃ³n)
            case 'r' :
            case 'R' : intervalo = intervaloSinValor.withValue("Deshabilitada: ReparaciÃ³n");
                break;
            case 'd' :
            case 'D' : intervalo = intervaloSinValor.withValue("Deshabilitada: DesinfecciÃ³n");
                break;
            case 'l' :
            case 'L' : intervalo = intervaloSinValor.withValue("Deshabilitada: Limpieza");
                break;
            default: intervalo = intervaloSinValor.withValue("Deshabilitada");
        }
        ColecIntervalosDeFechas = ColecIntervalosDeFechas.plus(intervalo);	// Agrega el intervalo a la colecciÃ³n;
    }


    /*  // En este metodo estoy comparando los Strings para ver si son ambos "Disponible" cuando en realidad es mejor no usar la palabra Disponible at all y tomar la ausencia de intervalo como disponibilidad
    public boolean isDisponible(PlainDate fecha) { // hay que hacer otro isDisponible para dos fechas (inicio y fin)
    	if (ColecIntervalosDeFechas.encloses(fecha)){ // Si la colecciÃ³n posÃ©e en sÃ­ misma la fecha, se la recorre
    		Iterator<ChronoInterval<PlainDate>> iterador = ColecIntervalosDeFechas.iterator();
    		while(iterador.hasNext()) {
    			ValueInterval<PlainDate, DateInterval,String> intervalo = (ValueInterval<PlainDate, DateInterval, String>)iterador; //se castea el iterador a ValueInterval ya que la coleccion no sabe quÃ© tipo de dato contiene
	    		if(intervalo.contains(fecha)) { // Si el iterador se encuentra en el intervalo que posee la fecha
	    			if(intervalo.getValue().equals("Disponible") || intervalo.getValue().equals("disponible")) {
	    				return true;
	    			}else return false;
	    		}
    		}
	    	System.out.println("La funcion isDisponible() de Habitacion no deberÃ­a llegar acÃ¡, se retorna false por si acaso");return false;
	    	// Esto nunca se deberia ejecutar ya que implica que encloses diga que el dia se encuentra en la coleccion pero que al recorrerla el intervalo no se encuentra (no entra al if)
    	}else return true; // El dia de hoy no se encuentra en ningun intervalo
    }*/
    /**
     * Determina si en el día de la fecha actual la habitación se encuentra disponible para ser ocupada por un pasajero.
     * @return TRUE si está disponible, FALSE si no lo está.
     */
    public boolean isDisponible() { // Disponible es "Disponible para ser ocupada"
        return isDisponible(PlainDate.nowInSystemTime());
    }
    /**
     * Determina si en el día de la fecha recibida la habitación se encuentra disponible para ser ocupada por un pasajero.
     * @param fecha Fecha por la cual se está consultando la disponibilidad.
     * @return TRUE si está disponible, FALSE si no lo está.
     */
    public boolean isDisponible (PlainDate fecha) {
        PlainDate fechaMasUno = fecha.plus(1, CalendarUnit.DAYS);// Sino después isDisponible(inicio,fin) tiene que restar un dia a la fecha y se arma quilombo
        return isDisponible(fecha, fechaMasUno);
    }
    /**
     * Determina si en la totalidad del intervalo recibido la habitación se encuentra disponible para ser ocupada por un pasajero.
     * @param inicio Fecha desde la cual se está consultando la disponibilidad.
     * @param fin Fecha hasta la cual se está consultando la disponibilidad.
     * @return TRUE si está disponible, FALSE si no lo está.
     */
    public boolean isDisponible (PlainDate inicio,PlainDate fin) {
        PlainDate finReal = fin.minus(1,CalendarUnit.DAYS);
        DateInterval intervaloRecibido = DateInterval.between(inicio, finReal);
        Iterator<ChronoInterval<PlainDate>> iterador = ColecIntervalosDeFechas.iterator();
        while(iterador.hasNext()) {
//    		@SuppressWarnings("unchecked")
            ValueInterval<PlainDate, DateInterval, String> intervaloCasteado = (ValueInterval<PlainDate, DateInterval, String>)iterador.next();
            DateInterval intervaloAComparar = intervaloCasteado.getBoundaries();
            if(intervaloRecibido.intersects(intervaloAComparar)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determina si en el día de la fecha actual la habitación se encuentra ocupada por un pasajero.
     * @return TRUE si está ocupada, FALSE si no lo está.
     */
    public boolean isOcupada() {//provisionalmente.. o no
        return !isDisponible();
    }
    /**
     * Determina si en el día de la fecha recibida la habitación se encuentra ocupada por un pasajero.
     * @param fecha Fecha por la cual se está consultando.
     * @return TRUE si está ocupada, FALSE si no lo está.
     */
    public boolean isOcupada(PlainDate fecha) {
        return !isDisponible(fecha);
    }
    /**
     * Determina si en algún día del intervalo recibido la habitación se encuentra ocupada por un pasajero.
     * @param inicio Fecha desde la cual se está consultando.
     * @param fin Fecha hasta la cual se está consultando.
     * @return TRUE si está ocupada, FALSE si no lo está.
     */
    public boolean isOcupada(PlainDate inicio,PlainDate fin) {
        return !isDisponible(inicio, fin);
    }
    /**
     * Ocupa una habitación para la fecha actual ("hoy" de cuando se ejecute el método).
     */
    public void ocupar(){
        setEstado(PlainDate.nowInSystemTime(),"Ocupada");
    }
    /**
     * Ocupa una habitación para la fecha recibida. Notese que ocupa la habitación solo por ese dia.
     * @param fecha Fecha para la cual se desea ocupar la habitación.
     */
    public void ocupar(PlainDate fecha) {
        setEstado(fecha,"Ocupada");
    }
    /**
     * Ocupa una habitación durante el intervalo recibido.
     * @param inicio Fecha a partir de la cual se desea ocupar la habitación.
     * @param fin Fecha hasta la cual se desea ocupar la habitación.
     */
    public void ocupar(PlainDate inicio,PlainDate fin) {
        PlainDate finReal = fin.minus(1,CalendarUnit.DAYS);
        setEstado(inicio,finReal,"Ocupada");
    }
    /**
     * Desocupa una habitación el dia de hoy.
     */
    public void desocupar(){
        desocupar(PlainDate.nowInSystemTime());
    }
    /**
     * Desocupa una habitación en una fecha determinada.
     * Notese que probablemente esté queriendo usar la versión de este método que desocupa en un intervalo ya que en este caso se desocupará solo en la fecha recibida, es decir si por ejemplo se encontrara ocupada de lunes a miercoles y se la desocupa el martes tanto el lunes como el miercoles se mantendrán ocupados.
     * @param fecha Fecha para la cual se desea desocupar la habitación.
     */
    public void desocupar(PlainDate fecha) {
        desocupar(fecha, fecha);;
    }
    /**
     * Desocupa una habitación en un intervalo determinado.
     * @param inicio Fecha a partir de la cual se desea ocupar la habitación.
     * @param fin Fecha hasta la cual se desea ocupar la habitación.
     */
    public void desocupar(PlainDate inicio, PlainDate fin){
        PlainDate finReal =fin.minus(1, CalendarUnit.DAYS);
        DateInterval intervalo = DateInterval.between(inicio, finReal);
        ColecIntervalosDeFechas.minus(intervalo);
    }

    /**
     * Habilita una habitación para ser utilizada el dia de hoy
     */
    public void habilitar(){
        habilitar(PlainDate.nowInSystemTime());
    }
    /**
     * Habilita una habitación para ser utilizada en una fecha determinada.
     * Notese que probablemente esté queriendo usar la versión de este método que habilita en un intervalo ya que en este caso se habilitará solo para la fecha recibida, es decir si por ejemplo se encontrara deshabilitada de lunes a miercoles y se la habilita el martes tanto el lunes como el miercoles se mantendrán deshabilitadas.
     * @param fecha Fecha para la cual se desea desocupar la habitación.
     */
    public void habilitar(PlainDate fecha) {
        PlainDate fechaMasUno = fecha.plus(1, CalendarUnit.DAYS);// Sino después habilitar(inicio,fin) tiene que restar un dia a la fecha y se arma quilombo
        habilitar(fecha,fechaMasUno);
    }
    /**
     * Habilita una habitación para ser utilizada en un intervalo determinado.
     * Notese que quizás la habitación se encuentre deshabilitada indefinidamente, en ese caso debe utilizarse habilitarIndefinidamente().
     * @param inicio Fecha a partir de la cual se desea habilitar la habitación.
     * @param fin Fecha hasta la cual se desea habilitar la habitación.
     */
    public void habilitar(PlainDate inicio,PlainDate fin) {
        PlainDate finReal =fin.minus(1, CalendarUnit.DAYS);
        DateInterval intervalo = DateInterval.between(inicio, finReal);
        ColecIntervalosDeFechas = ColecIntervalosDeFechas.minus(intervalo);
    }
    /**
     * Habilita una habitación para ser utilizada en un intervalo infinito a partir de la fecha ingresada.
     * Nota: Este método "Pisará" todos los intervalos posteriores a la fecha ingresada, es responsabilidad del usuario del método chequear que esto no sea un problema.
     * @param fechaAPartirDeLaCual Fecha a partir de la cual se desea habilitar indefinidamente una habitación.
     */
    public void habilitarIndefinidamente(PlainDate fechaAPartirDeLaCual) {
        DateInterval intervalo = DateInterval.since(fechaAPartirDeLaCual);
        ColecIntervalosDeFechas = ColecIntervalosDeFechas.minus(intervalo);
    }
    /**
     * Devuelve una habitación en forma de String.
     * @return Objeto de tipo Habitación
     */
    public String mostrarHabitacion() {
        String eString = (toString()+"\n Estado Actual: "+getEstado(PlainDate.nowInSystemTime()));
        return eString;
    }
    /**
     * Muestra el estado de una habitación por pantalla.
     * @return Objeto de tipo Habitación
     */
    public void mostrarHabitacionPorPantalla() {
        System.out.println(toString());
        PlainTime ahora = PlainTime.nowInSystemTime();
        PlainTime inicioLimpieza = PlainTime.of(10, 30);
        PlainTime finLimpieza = PlainTime.of(15,0);
        if(getEstado().equals("Disponible") && ahora.isAfter(inicioLimpieza) && ahora.isBefore(finLimpieza)) {
            System.out.print(" Estado Actual: "+getEstado(PlainDate.nowInSystemTime())+" - En Limpieza (Hasta las 15:00 hs.)");
        }
        else System.out.println(" Estado Actual: "+getEstado(PlainDate.nowInSystemTime()));
    }

    @Override
    public String toString() {
        return "\n Numero de Habitacion: " + numHabitacion + "\n Capacidad: " + capacidad + " personas" + "\n Tipo: " +
                tipo + "\n Precio diario: $" + precioDiario;
    }
    @Override
    public int compareTo(Habitacion hab) {
        if(capacidad > hab.getCapacidad()) {
            return 1;
        }
        else if(capacidad < hab.getCapacidad()) {
            return -1;
        }
        return 0;
    }
}