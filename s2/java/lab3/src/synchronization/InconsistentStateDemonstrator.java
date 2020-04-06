package synchronization;

import java.util.Random;

/**
 * Demonstrates the inconsistent state problem in the most simple setting: two
 * threads sharing an object with an invariant; one thread update the object
 * while the other read the object.
 *
 * @author Jean-Michel Busca
 */
public class InconsistentStateDemonstrator {

    // the shared object
    private static final MyObject sharedObject = new MyObject();

    public static void main(String[] args) {
        // start a thread that updates the shared object
        Thread thread = new Thread(() -> {
            Random random = new Random();
            for (int i = 0; i < 5_000_000; i++) {
                sharedObject.setA(random.nextInt(1000));
            }
        });
        thread.start();

        // check the object
        for (int i = 0; i < 5_000_000; i++) {
            int a = sharedObject.getA();
            int b = sharedObject.getB();
            if (a + b != 100) {
                System.out.println("object is in an inconsistent state!");
            }
        }
    }
}
