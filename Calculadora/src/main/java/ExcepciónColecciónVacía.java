/**
 * Excepcion Colección Vacía

 * Se define una clase propia para el manejo de excepción en los casos que la 
 * colección o estructura de datos esté vacía.
 * @author Ceci Leon, Aniel Orihuela, Rodrigo Flores, Fernando Barrios
 * @version 1.1
 */
public class ExcepciónColecciónVacía extends RuntimeException{

    /**
     * Constructor de excepcion con clase padre: RuntimeException
     */
    public ExcepciónColecciónVacía(){
        super("Colección vacía");
    }

    /**
     * Raise exception 
     * @param mensaje // Mensaje de error
     */
    public ExcepciónColecciónVacía(String mensaje){
        super(mensaje);
    }
}


