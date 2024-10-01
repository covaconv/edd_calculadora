/**
 
 * Se define una clase propia para el manejo de excepción en los casos que la 
 * colección o estructura de datos esté vacía.
 */
public class ExcepciónColecciónVacía extends RuntimeException{

    public ExcepciónColecciónVacía(){
        super("Colección vacía");
    }

    public ExcepciónColecciónVacía(String mensaje){
        super(mensaje);
    }
}


