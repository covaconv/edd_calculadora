/** 
 * Pila
 * 
 * Clase que implementa una pila genérica usando un arreglo genérico.
 * @author Ceci Leon, Aniel Orihuela, Rodrigo Flores, Fernando Barrios
 * @version 1.1
 */
public class PilaA <T> implements PilaADT <T>{
    private T colec[];
    private int tope;
    private final int MAX = 10;

    /**
     * Se construye un arreglo de objetos y se lo convierte explícitamente a tipo T.
     * Inicialmente la pila está vacía, lo que se indica con tope igual a -1.
     */
    public PilaA() {
        colec = (T[]) (new Object[MAX]);
        tope = -1;
    }
    
    /**
     * Se construye un arreglo de objetos y se lo convierte explícitamente a tipo T.
     * Inicialmente la pila está vacía, lo que se indica con tope igual a -1.
     * @param max (<b>int</b>) El espacio máximo reservado.
     */
    public PilaA(int max) {
        colec = (T[]) (new Object[max]);
        tope = -1;
    }
    
    /**
     * Agrega el dato en el tope, redefiniendo el valor de éste. Si la pila está llena, 
     * se construye un arreglo de mayor capacidad y se copian los elementos de la pila 
     * a éste.
     * @param dato (<b>T</b>) Elemento que se agrega a la pila.
     */
    public void push(T dato) {
        if (tope == colec.length - 1) 
            aumentaCapacidad();
        tope++;
        colec[tope] = dato;
    }

    /**
     * Elimina y regresa el elemento que está en el tope de la pila, redefiniendo el valor 
     * del tope. Si la pila está vacía lanza una excepción.
     * @return (<b>T</b>) Elemento que está en el tope de la pila.
     */
    public T pop() {
        if (isEmpty())
            throw new ExcepciónColecciónVacía("Pila vacía");
        else{
            T dato = colec[tope];
            tope--;
            return dato;
        }
    }
    
    /**
     * Regresa el elemento que está en el tope.
     * Si la pila está vacía lanza una excepción.
     * @return (<b>T</b>) Elemento que está en el tope de la pila.
     */
    public T peek() { 
        if (isEmpty())
            throw new ExcepciónColecciónVacía("Pila vacía");
        else
            return colec[tope];
    }
    
    /**
     * Revisa si la pila está vacía.
     * @return <ul>
     * <li><b>true:</b> si la pila está vacía.</li>
     * <li><b>false.</b> si la pila <b>no</b> está vacía.</li>
     */
    public boolean isEmpty() {
        return tope == -1;
    }
    
    /**
     * Método auxiliar que construye un arreglo de mayor tamaño y copia en él todos
     * los elementos de la pila, asignando al arreglo colec la referencia del nuevo
     * arreglo.
     */
    private void aumentaCapacidad(){
        T nuevo[] = (T[]) (new Object[colec.length * 2]);
        int i;
        
        for (i= 0; i <= tope; i++)
            nuevo[i] = colec[i];        
        colec = nuevo;
    }
}
