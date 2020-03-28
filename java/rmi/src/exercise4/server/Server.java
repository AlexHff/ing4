package exercise4.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import exercise4.Computer;

import static exercise4.server.Registry.REGISTRY_PORT;

public class Server {
    private static final String SERVICE_NAME = "Computer";

    public static void main(String[] args) throws Exception {
        Computer engine = new ComputerEngine();
        Computer stub = (Computer) UnicastRemoteObject.exportObject(engine, 0);

        Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
        registry.rebind(SERVICE_NAME, stub);

        System.out.println("server: ready");
    }
}
