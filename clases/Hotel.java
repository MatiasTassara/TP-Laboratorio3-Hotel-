package clases;

/**
 * Para poder instanciar la clase hotel hay que declararlo de la siguiente forma:
 * Hotel hotel = Hotel.getSingletonInstance();
 */
public class Hotel {

    private String nombre;
    private String direccion;
    private String CUIT;
    private LoginMenu loginMenu;
    private static BaseDeDatos baseDeDatos;
    /**
     * Constructor privado para que sea accesible solo desde el metodo estatico getSingletonInstance()
     * @param nombre
     * @param direccion
     * @param CUIT
     */
    private Hotel(String nombre,String direccion,String CUIT){
        this.nombre = nombre;
        this.direccion = direccion;
        this.CUIT = CUIT;
        loginMenu = new LoginMenu();
        baseDeDatos = new BaseDeDatos();
    }

    /**
     * variable estatica que recibe la instancia (en el main)
     */
    private static Hotel hotel;

    /**
     * Patron singleton para que pueda ser instanciado solo una vez
     * @param nombre
     * @param direccion
     * @param CUIT
     * @return
     */
    public static Hotel getSingletonInstance(String nombre,String direccion,String CUIT){
        if(hotel == null){
            hotel = new Hotel(nombre,direccion,CUIT);
        }
        else{
            System.out.println("No se puede crear mas de un hotel");
        }
        return hotel;
    }

    /**
     * PREVIENE QUE LA BASE DE DATOS SEA CLONADA (acompania al singleton para asegurar una sola instancia)
     * @return
     */
    @Override
    protected Object clone(){
        try {
            throw new CloneNotSupportedException();
        } catch (CloneNotSupportedException ex) {
            System.err.println("No se puede clonar un objeto de tipo Hotel");
        }
        return null;
    }

    public LoginMenu getLoginMenu() {
        return loginMenu;
    }
}