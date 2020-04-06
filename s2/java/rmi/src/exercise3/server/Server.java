package exercise3.server;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import exercise3.PropertyRepository;

import static exercise1.server.Registry.REGISTRY_PORT;

public class Server {
    private static final String SERVICE_NAME = "PropertyRepository";

    public static void main(String[] args) throws Exception {
        System.out.println("server: running on host " + InetAddress.getLocalHost());

        PropertyRepository repo = new MyPropertyRepository();
        System.out.println("server: instanciated Repository");

        PropertyRepository stub = (PropertyRepository) UnicastRemoteObject.exportObject(repo, 0);
        System.out.println("server: generated skeleton and stub");

        Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
        registry.rebind(SERVICE_NAME, stub);
        System.out.println("server: registered remote object's stub");

        System.out.println("server: ready");
    }
}
