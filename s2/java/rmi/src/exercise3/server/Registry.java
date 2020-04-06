package exercise3.server;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;

public class Registry {
    public static final int REGISTRY_PORT = 1099; // 1099 = default value

    public static synchronized void main(String[] args) throws Exception {
        System.out.println("registry: running on host " + InetAddress.getLocalHost());

        LocateRegistry.createRegistry(REGISTRY_PORT);
        System.out.println("registry: listening on port " + REGISTRY_PORT);

        Registry.class.wait();
        System.out.println("registry: exiting (should not happen)");
    }
}
