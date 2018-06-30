package clases;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
/**
 *
 * Clase abastracta usuario que se compone por atributos y métodos que tienen en comun los
 * administradores, los concerjes y los pasajeros.
 *
 */
public abstract class  Usuario implements Serializable{

    private String dni;
    private String nombre;
    private String apellido;
    private boolean estadoDeAlta;
    private String password;

    public Usuario()
    {
        dni = " ";
        nombre =" ";
        apellido = " ";
    }


    public Usuario(String dni, String nombre, String apellido,String password)
    {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estadoDeAlta = true;
        this.password = password;

    }

    public boolean isEstadoDeAlta() {
        return estadoDeAlta;
    }

    public String getPassword() {
        return password;
    }

    public void setDni(String dni)
    {
        this.dni = dni;
    }
    public String getDni()
    {
        return dni;
    }
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public void setEstadoDeAlta(boolean estadoDeAlta) {
        this.estadoDeAlta = estadoDeAlta;
    }

    public String getNombre()
    {
        return nombre;
    }
    public void setApellido(String apellido)
    {
        this.apellido = apellido;
    }
    public String getApellido()
    {
        return apellido;
    }
    public void ponerEnBaja(){
        if(estadoDeAlta){
            estadoDeAlta = false;
        }

    }
    public void ponerEnAlta(){
        if(!estadoDeAlta){
            estadoDeAlta = true;
        }

    }

    @Override
    public String toString() {

        return "\nDNI: " + dni + "\nNombre: " + nombre + "\nApellido: " + apellido;
    }
    /**
     * metodo abstracto que sera implementado en cada una de las subclases.
     * @return
     * @throws JSONException
     */
    public abstract JSONObject getJson() throws JSONException;





}