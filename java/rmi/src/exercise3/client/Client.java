package exercise3.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import exercise3.PropertyRepository;

public class Client {
    private static final String SERVICE_NAME = "PropertyRepository";
    private static final String SERVICE_HOST = "localhost";
    private static final int REGISTRY_PORT = 1099;

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry(SERVICE_HOST, REGISTRY_PORT);
        System.out.println("client: retrieved registry");

        PropertyRepository repo = (PropertyRepository) registry.lookup(SERVICE_NAME);
        System.out.println("client: retrieved Repository stub");

        repo.setProperty("Bob", "1234");
        System.out.println(repo.getProperty("Bob"));

        System.out.println("client: exiting");
    }
}
