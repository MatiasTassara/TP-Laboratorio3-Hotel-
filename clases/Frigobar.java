package clases;

import java.util.*;
import java.io.*;

/**
 * Esta clase define los comportamientos básicos de un frigobar de la habitación de un hotel, lugar en donde el "Frigobar" se encuentra enmarcado.
 * El frigobar posee solo dos atributos:
 * @param saldo El saldo acumulado del frigobar. Este inicia en cero al comienzo de la estadía, acumula lo que se vaya consumiendo y vuelve a cero cuando el pasajero abona.
 * @param productos Es un HashMap que contiene el los productos ofrecidos en el frigobar en forma de String y el precio de cada uno en forma de Double.
 *
 */
public class Frigobar implements Serializable{

    private double saldo;
    private  HashMap<String,Double> productos;

    /**
     * Constructor de Frigobar. Inicia con el saldo en 0 y el listado y valor de los productos cargados por defecto.
     */
    public Frigobar() {
        this.saldo = 0;
        productos = new HashMap<>();
        agregarAlInventario();
    }

    public  HashMap<String, Double> getProductos() {
        return productos;
    }

    /**
     * Devuelve el saldo acumulado hasta el momento en el Frigobar.
     * @return Saldo acumulado
     */
    public double getSaldo() {
        return saldo;
    }
    /**
     * Agrega un listado harcodeado de tanto productos como sus respectivos valores al inventario
     */
    public void agregarAlInventario() {// Los productos se encuentran escritos con minuscula ya que en consumirProducto el
        productos.put("coca cola",30.5);// parámetro recibido se interpreta en minuscula sin importar como haya sido ingresado
        productos.put("agua mineral", 30.0);
        productos.put("chocolate",25.0);
        productos.put("cerveza",42.5);
    }
    /**
     * Carga el equivalente del valor consumido por el pasajero al saldo.
     * @param producto Producto consumido, estos pueden ser:  Coca Cola | Agua mineral | Chocolate | Cerveza
     * @param cantidad Cantidad de unidades consumidas.
     */
    public void consumirProduto(String producto,byte cantidad) {
        String productoConsumido = producto.toLowerCase(); // Lo convierte a minuscula para asegurarse de que esté bien escrito
        saldo += productos.get(productoConsumido) * cantidad;
    }
    /**
     * Vuelve el saldo del frigobar a cero, útil al momento de pagar.
     * Nótese que este método no devuelve la cantidad saldada así que para mantener la información previamente se deberá ejecutar getSaldo() para mantener esa información.
     */
    public void cancelarSaldo() {
        saldo = 0;
    }

}