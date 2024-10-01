

/**
 
 * La clase representa de manera muy simplificada una calculadora que puede convertir
 * una expresión dada en notación prefija a notación postfija y, posteriormente, 
 * evaluarla. Se usan las pilas como estructuras de datos auxiliares a la solución
 * del problema.
 * La calculadora sólo tiene 2 atributos: uno para almacenar la expresión original
 * y el segundo para almacenar el resultado, en caso de que la expresión pueda evaluarse.
 */
public class Calculadora {
    private String entrada;  // Es una expresión dada en notación infija
    private double resultado;

    public Calculadora() {
    }
    
    // Se construye un objeto tipo calculadora y se le asigna un valor a la expresión infija
    public Calculadora(String entrada) {
        this.entrada = entrada;
    }

    // Método para cambiar el valor de la expresión que se evaluará por medio de la calculadora
    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }
    
    // Método para obtener el resultado
    public double getResultado(){
        return resultado;
    }
    
    /* Método que tiene el control de las operaciones que lleva a cabo la calculadora
     * para evaluar la expresión. Si la expresión se puede evaluar deja el resultado en
     * el atributo resultado y regresa true. En caso contrario regresa false.
     */
    public boolean calcula(){
        boolean resp;
       
        resp = revisa(); // Revisa que los paréntesis estén bien balanceados
        if (resp){
            String[] elementos = obtieneTokens(); // Obtiene los elementos de la expresión
            elementos = conviertePostfija(elementos); // Convierte a postfija
            resultado = evalúa(elementos); // Evalúa la expresión ya en notación postfija
        }
        return resp;
    }
    
    /* Método auxiliar que revisa si la expresión dada en notación infija tiene los 
     * paréntesis bien balanceados. Es decir, si el número de paréntesis izquierdos 
     * concuerda con el número de paréntesis derechos.
     * Utiliza un objeto tipo PilaE para almacenar los paréntesis izquierdos temporalmente.
     */
    private boolean revisa(){
        PilaADT<Character> pila = new PilaA<>();
        boolean resp;
        int i, n;

        n = entrada.length();
        i= 0;
        resp = true;
        while (i < n && resp){
            if (entrada.charAt(i) == '(')
                pila.push(entrada.charAt(i)); // Guarda el paréntesis izquierdo
            else
                if (entrada.charAt(i) == ')') // Si es paréntesis derecho
                    if (pila.isEmpty()) // intenta sacar su correspondiente izquierdo 
                        resp = false;   // de la pila. Si no hay, altera la variable
                    else                // para no seguir analizando la expresión.
                        pila.pop();    
            i++;
        }
        return resp && pila.isEmpty();
    }
    
    /* Método auxiliar que permite separar -por medio del método split() de la clase String
     * de Java- la cadena dada y guarda cada uno de sus elementos (operadores, operandos 
     * y paréntesis) en un arreglo de cadenas.
     */ 
    private String[] obtieneTokens(){
      return entrada.split(" ");
    }
    
    /* Método auxiliar que recibe un arreglo de cadenas que representa a una cadena en
     * notación infija y regresa otro arreglo con la misma expresión pero ahora en
     * notación postfija. Usa un objeto tipo PilaE para almacenar temporalemente algunos
     * elementos de la expresión. 
     */        
    private String[] conviertePostfija(String elementos[]){
        String postfija[] = new String[elementos.length];
        PilaADT <String> pila = new PilaA<>();
        int e, p, n;
        
        e = 0;
        p = 0;
        n = elementos.length;
        while (e < n){
            if (elementos[e].equals("(")) // Es paréntesis izquierdo
                pila.push(elementos[e]);
            else
                if (elementos[e].equals(")")){ // Es paréntesis derecho
                    while (!pila.peek().equals("(")){
                        postfija[p] = pila.pop();
                        p++;
                    }
                    pila.pop();
                }
                else
                    if (noEsOperador(elementos[e])){ // Es un operando
                        postfija[p] = elementos[e];
                        p++;
                    }
                    else {  // Es un operador
                        while (!pila.isEmpty() && prioridad(pila.peek()) > prioridad(elementos[e])){
                            postfija[p] = pila.pop();
                            p++;
                        }
                        pila.push(elementos[e]);
                    }
            e++;          
       }
        while (!pila.isEmpty()){
            postfija[p] = pila.pop();
            p++;
        }
        return postfija;
    }
    
    // Método auxiliar que regresa true si la cadena recibida no es un operador
    private boolean noEsOperador(String dato){
        return !dato.equals("+") && !dato.equals("-") && !dato.equals("*") && !dato.equals("/");
    }
    
    /* Método auxiliar para el manejo de las prioridades de los operadores. Regresa 0,
     * el valor más pequeño, cuando el dato dado es un "(". De esta manera
     * el "(" sólo se saca de la pila cuando se encuentre un ")".
     */
    private int prioridad(String dato){
        int resultado = 0; // En caso de que el dato sea un paréntesis izquierdo
        
        switch (dato.charAt(0)){
            case '+':
            case '-': resultado = 1;
                break;
            case '*':
            case '/': resultado = 2;
        }
        return resultado;
    }
    
    /* Método auxiliar para evaluar una expresión dada en notación postfija.
     * Usa un objeto tipo PilaE para almacenar temporalmente los operandos y los 
     * resultados parciales que se van obteniendo. 
     * Regresa un dato tipo double.
     */
    private double evalúa(String postfija[]){
        PilaADT<Double> pila = new PilaA<>();
        double resul, op1, op2;
        int i;
        
        resul = 0;
        i = 0;
        while (i < postfija.length && postfija[i] != null){
            if (noEsOperador(postfija[i])) // Es operando
                pila.push(Double.valueOf(postfija[i]));
            else { // Es operador
                op2 = pila.pop();
                op1 = pila.pop();
                switch (postfija[i].charAt(0)){
                    case '+': resul = op1 + op2;
                        break;
                    case '-': resul = op1 - op2;
                        break;
                    case '*': resul = op1 * op2;
                        break;
                    case '/': if (op2 == 0) // Si el denominador es 0 se lanza una excepción
                                    throw new RuntimeException();
                              resul = op1 / op2;
                }
                pila.push(resul);                        
                }
            i++;
        }
          return pila.pop();            
        }    
}
