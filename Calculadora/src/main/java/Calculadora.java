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
    private String[] conviertePostfija(String elementos[]) {
        String postfija[] = new String[elementos.length];
        PilaADT<String> pila = new PilaA<>();
        int e, p, n;
    
        e = 0;
        p = 0;
        n = elementos.length;
        while (e < n) {
            if (elementos[e].equals("(")) {
                pila.push(elementos[e]);  // Apilar el paréntesis izquierdo
            } else if (elementos[e].equals(")")) {
                // Manejar el caso del paréntesis derecho
                while (!pila.isEmpty() && !pila.peek().equals("(")) {
                    postfija[p] = pila.pop();
                    p++;
                }
                if (!pila.isEmpty()) {
                    pila.pop(); // Elimina el paréntesis izquierdo
                }
            } else if (elementos[e].equals("√")) {
                // Manejar √ junto con un posible paréntesis
                if (elementos[e + 1].equals("(")) { // Si el siguiente es un paréntesis
                    pila.push("√"); // Apilar √ y tratar la expresión dentro de los paréntesis
                } else {
                    // Si no hay paréntesis, manejarlo como operación unaria simple
                    postfija[p] = elementos[++e]; // Toma el siguiente operando
                    p++;
                    postfija[p] = "√"; // Luego colocamos el operador de raíz cuadrada
                    p++;
                }
            } else if (noEsOperador(elementos[e])) { // Es un operando
                postfija[p] = elementos[e];
                p++;
            } else { // Es un operador (+, -, *, /, ^)
                while (!pila.isEmpty() && prioridad(pila.peek()) >= prioridad(elementos[e])) {
                    postfija[p] = pila.pop();
                    p++;
                }
                pila.push(elementos[e]);
            }
            e++;
        }
    
        // Sacar todos los operadores restantes de la pila
        while (!pila.isEmpty()) {
            postfija[p] = pila.pop();
            p++;
        }
    
        return postfija;
    }
    
    // Método auxiliar que regresa true si la cadena recibida no es un operador
    private boolean noEsOperador(String dato){
        return !dato.equals("+") && !dato.equals("-") && !dato.equals("*") && !dato.equals("/") && !dato.equals("^") && !dato.equals("√");
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
                break;
            case '√':
            case '^': resultado = 3;
                break;
            case '(': resultado = 0;
        }
        return resultado;
    }
    
    /* Método auxiliar para evaluar una expresión dada en notación postfija.
     * Usa un objeto tipo PilaE para almacenar temporalmente los operandos y los 
     * resultados parciales que se van obteniendo. 
     * Regresa un dato tipo double.
     */
    private double evalúa(String postfija[]) {
        PilaADT<Double> pila = new PilaA<>();
        double resul, op1, op2;
        int i = 0;
    
        resul = 0;
        op1 = 0;
        op2 = 0;
        while (i < postfija.length && postfija[i] != null) {
            if (!postfija[i].trim().isEmpty()) {
                if (noEsOperador(postfija[i])) { // Si es un operando
                    pila.push(Double.valueOf(postfija[i])); // Convertir a número
                } else { // Es un operador
                    if (postfija[i].equals("√")) { // Operación unaria (raíz cuadrada)
                        op2 = pila.pop(); // Solo un operando para √
                        resul = Math.sqrt(op2); // Aplica la raíz cuadrada
                    } else { // Operaciones binarias
                        op2 = pila.pop(); // Extrae el segundo operando
                        op1 = pila.pop(); // Extrae el primer operando
    
                        switch (postfija[i].charAt(0)) {
                            case '+': resul = op1 + op2;
                                break;
                            case '-': resul = op1 - op2;
                                break;
                            case '*': resul = op1 * op2;
                                break;
                            case '/': 
                                if (op2 != 0) {
                                    resul = op1 / op2;
                                }
                                break;
                            case '^': resul = Math.pow(op1, op2);
                                break;
                        }
                    }
                    pila.push(resul); // Empuja el resultado a la pila
                }
            }
            i++;
        }
        // Verificar el estado de la pila al final
        if (!pila.isEmpty()) {
            return pila.pop(); // El resultado final está en la pila
        } else {
            return 0; // Devuelve un valor predeterminado en caso de error
        }
    }
}