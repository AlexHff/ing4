package exercise2.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;

import exercise2.Sorter;

/**
 * Client program.
 *
 * Note: In order to retrieve the stub of the remote object, the client needs to
 * know (1) the name of the remote object, (2) the machine that hosts it.
 *
 */
public class Client {

    //
    // CONSTANTS
    //
    private static final String SERVICE_NAME = "Sorter";
    private static final String SERVICE_HOST = "192.168.1.76";
    private static final int REGISTRY_PORT = 1099;

    //
    // MAIN
    //
    public static void main(String[] args) throws Exception {

        // locate the registry that runs on the remote object's server
        Registry registry = LocateRegistry.getRegistry(SERVICE_HOST, REGISTRY_PORT);
        System.out.println("client: retrieved registry");

        // retrieve the stub of the remote object by its name
        Sorter sorter = (Sorter) registry.lookup(SERVICE_NAME);
        System.out.println("client: retrieved Sorter stub");

        // call the remote object to perform sorts and reverse sorts
        List<String> list = Arrays.asList("3", "5", "1", "2", "4");
        System.out.println("client: sending " + list);

        list = sorter.sort(list);
        System.out.println("client: received " + list);

        list = Arrays.asList("mars", "saturne", "neptune", "jupiter");
        System.out.println("client: sending " + list);

        list = sorter.reverseSort(list);
        System.out.println("client: received " + list);

        // main terminates here
        System.out.println("client: exiting");
    }
}
