package synchronization;

/**
 * Demonstrates the lost update problem in the most simple setting: two threads
 * sharing an integer variable; both threads increment the variable.
 *
 * @author Jean-Michel Busca
 */
public class LostUpdateDemonstrator {

    // the shared variable
    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {

        long time = System.currentTimeMillis();

        // start a thread that increments the counter 1,000,000 times
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 50_000_000; i++) {
                counter += 1;
            }
        });
        thread.start();

        // have the main thread increment the counter 1,000,000 times too
        for (int i = 0; i < 50_000_000; i++) {
            counter += 1;
        }

        // wait for the thread to complete and print the value of the counter
        thread.join();
        System.out.println("counter=" + counter);

        time = System.currentTimeMillis() - time;
        System.out.println("elapsed: " + time + "ms");
    }
}
