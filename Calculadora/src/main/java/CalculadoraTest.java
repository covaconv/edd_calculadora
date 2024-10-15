import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CalculadoraTest {

    @Test
    public void testCalculadoraSinEntrada() {
        Calculadora calc = new Calculadora();
        assertNull(calc.getResultado(), "El resultado debería ser nulo al crear una calculadora sin entrada.");
    }

    @Test
    public void testCalculadoraConEntrada() {
        Calculadora calc = new Calculadora("2 + 2");
        assertTrue(calc.calcula(), "La calculadora debería poder evaluar una expresión válida.");
        assertEquals(4.0, calc.getResultado(), 0.001, "El resultado de 2 + 2 debería ser 4.0.");
    }

    @Test
    public void testExpresionInvalida() {
        Calculadora calc = new Calculadora("2 + ");
        assertFalse(calc.calcula(), "La calculadora debería detectar una expresión inválida.");
    }

    @Test
    public void testBalanceoParentesisValido() {
        Calculadora calc = new Calculadora("(2 + 3) * 4");
        assertTrue(calc.calcula(), "La calculadora debería poder evaluar una expresión con paréntesis balanceados.");
        assertEquals(20.0, calc.getResultado(), 0.001, "El resultado de (2 + 3) * 4 debería ser 20.0.");
    }

    @Test
    public void testBalanceoParentesisInvalido() {
        Calculadora calc = new Calculadora("(2 + 3 * 4");
        assertFalse(calc.calcula(), "La calculadora debería detectar paréntesis desbalanceados.");
    }

    @Test
    public void testRaizCuadrada() {
        Calculadora calc = new Calculadora("√ ( 16 ) ");
        assertTrue(calc.calcula(), "La calculadora debería poder evaluar una raíz cuadrada.");
        assertEquals(4.0, calc.getResultado(), 0.001, "El resultado de √16 debería ser 4.0.");
    }

    @Test
    public void testPotencia() {
        Calculadora calc = new Calculadora("2 ^ 3");
        assertTrue(calc.calcula(), "La calculadora debería poder evaluar una operación de potencia.");
        assertEquals(8.0, calc.getResultado(), 0.001, "El resultado de 2^3 debería ser 8.0.");
    }

    @Test
    public void testDivisionPorCero() {
        Calculadora calc = new Calculadora("10 / 0");
        assertFalse(calc.calcula(), "La calculadora debería detectar una división por cero.");
    }
}
