package exercise4.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    private static final String SERVICE_HOST = "localhost";
    private static final int REGISTRY_PORT = 1099;

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry(SERVICE_HOST, REGISTRY_PORT);
        System.out.println("client: retrieved registry");

        MyCounter counter = (MyCounter) registry.lookup("Task");
        System.out.println("client: retrieved stub");
        counter.execute();

        System.out.println("client: exiting");
    }
}
