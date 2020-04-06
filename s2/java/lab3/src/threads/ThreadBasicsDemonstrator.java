package threads;

/**
 * Demonstrates the basic operation of threads.
 *
 * @author Jean-Michel Busca
 */
public class ThreadBasicsDemonstrator {
    public static void main(String[] args) throws InterruptedException {
        // Note: when the JVM initializes, it starts a non-daemon thread whose
        // purpose is to execute the main method of the class
        System.out.println("method main: run by " + Thread.currentThread());

        // start thread1 (which is an instance of the MyThread class)
        System.out.println("method main: starting thread1");
        Thread thread1 = new MyThread("thread1");
        thread1.setDaemon(true);
        thread1.start();

        // start thread2 (using an instance of the MyRunnable class)
        System.out.println("method main: starting thread2");
        Thread thread2 = new Thread(new MyRunnable(), "thread2");
        thread2.setDaemon(true);
        thread2.start();

        Runnable runnable = () -> {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < 10_000_000; i += 1) {
                if (i % 100_000 == 0) {
                    System.out.println(name + ": i=" + i);
                }
            }
        };
        Thread thread3 = new Thread(runnable, "thread3");
        thread3.setDaemon(true);
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("method main: done");
    }
}
