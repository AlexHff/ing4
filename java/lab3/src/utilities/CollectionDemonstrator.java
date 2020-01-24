package utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates how the collection behave when accessed by multiple threads.
 *
 * @author Jean-Michel Busca
 */
public class CollectionDemonstrator {

    public static void main(String[] args) throws InterruptedException {
        // create a shared collection
        List<String> list = new ArrayList<>();

        // start two threads that fill-in the collection
        Runnable task = () -> {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < 100_000; i++) {
                list.add(name + i);
            }
        };

        System.out.println("main: starting thread1");
        Thread thread1 = new Thread(task, "thread1");
        thread1.start();

        System.out.println("main: starting thread2");
        Thread thread2 = new Thread(task, "thread2");
        thread2.start();

        // wait for the threads to complete
        thread1.join();
        thread2.join();

        // does the list contain 2 * 100_000 elements?
        System.out.println("main: list.size()=" + list.size());
    }
}
