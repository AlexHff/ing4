package threads;

/**
 *
 * @author Jean-Michel Busca
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        for (int i = 0; i < 10_000_000; i += 1) {
            if (i % 100_000 == 0) {
                System.out.println(name + ": i=" + i);
            }
        }
    }
}
