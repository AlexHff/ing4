package exercise4.server;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import exercise4.Computer;
import exercise4.Task;

import static exercise4.server.Registry.REGISTRY_PORT;

public class Server implements Computer {
    private static final String SERVICE_NAME = "PropertyRepository";

    @Override
    public <T> T execute(Task<T> task) {
        return task.execute();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("server: running on host " + InetAddress.getLocalHost());

        /*
         * PropertyRepository repo = new MyPropertyRepository();
         * System.out.println("server: instanciated Repository");
         * 
         * PropertyRepository stub = (PropertyRepository)
         * UnicastRemoteObject.exportObject(repo, 0);
         * System.out.println("server: generated skeleton and stub");
         * 
         * Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
         * registry.rebind(SERVICE_NAME, stub);
         * System.out.println("server: registered remote object's stub");
         */

        System.out.println("server: ready");
    }
}
