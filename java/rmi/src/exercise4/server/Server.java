package exercise4.server;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static exercise4.server.Registry.REGISTRY_PORT;

public class Server {
    private static final String SERVICE_NAME = "Task";

    public static void main(String[] args) throws Exception {
        System.out.println("server: running on host " + InetAddress.getLocalHost());

        TaskManager manager = new TaskManager();
        TaskManager stub = (TaskManager) UnicastRemoteObject.exportObject(manager, 0);

        Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
        registry.rebind(SERVICE_NAME, stub);

        System.out.println("server: ready");
    }
}
