package synchronization;

/**
 * Demonstrates the memory inconsistency problem in the most simple setting: two
 * threads sharing a boolean variable; one thread reads the variable while the
 * other writes to it.
 * <p>
 * Example drawn from "Effective Java". Joshua Blcoh.3rd Edition.
 */
public class MemoryInconsistencyDemonstrator {

    // the shared variable
    private static boolean stopRequested = false;

    public static void main(String[] args) throws InterruptedException {
        // start a thread that loops while waiting to be stopped
        Thread thread = new Thread(() -> {
            int i = 0;
            while (!stopRequested) {
                i += 1;
            }
        });
        thread.start();

        // after 2s, request the thread to stop
        Thread.sleep(2 * 1000);
        stopRequested = true;
        System.out.println("main: stop requested");

        // the thread dos not stop: the JVM continues running
    }
}
