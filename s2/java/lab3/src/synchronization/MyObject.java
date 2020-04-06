package synchronization;

/**
 * An object whose state must satisfy an invariant, e.g. a + b = 100
 * <p>
 * Note: An invariant is a property that must hold at all times.
 * <p>
 * This example is delibaretely simplistic.
 *
 * @author Jean-Michel Busca
 */
public class MyObject {
    private int a = 50;
    private int b = 50;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
        b = 100 - a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
        a = 100 - b;
    }
}
