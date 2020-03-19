package exercise1.server;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import exercise1.Sorter;

import static exercise1.server.Registry.REGISTRY_PORT;

/**
 * Server program.
 *
 * Note: After the main method exits, the JVM is still running. This is because
 * the skeleton implements a non-daemon listening thread, which waits for
 * incoming requests forever.
 *
 */
public class Server {

    //
    // CONSTANTS
    //
    private static final String SERVICE_NAME = "Sorter";

    //
    // MAIN
    //
    public static void main(String[] args) throws Exception {
        System.setProperty("java.rmi.server.hostname", "127.0.1.1");

        // check the name of the local machine (two methods)
        System.out.println("server: running on host " + InetAddress.getLocalHost());
        System.out.println("server: hostname property " + System.getProperty("java.rmi.server.hostname"));

        // instanciate the remote object
        Sorter sorter = new SimpleSorter();
        System.out.println("server: instanciated SimpleSorter");

        // create a skeleton and a stub for that remote object
        // note: the stub is for client use only; it will be passed to the client via
        // the registry
        Sorter stub = (Sorter) UnicastRemoteObject.exportObject(sorter, 0);
        System.out.println("server: generated skeleton and stub");

        // register the remote object's stub in the registry
        Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
        registry.rebind(SERVICE_NAME, stub);
        System.out.println("server: registered remote object's stub");

        // main terminates here, but the JVM still runs because of the skeleton
        System.out.println("server: ready");
    }
}
