package exercise1.server;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;

/**
 * Registry program.
 *
 * This program is the equivalent of the rmigistry.exe excutable provided as
 * part of the RMI runtime.
 *
 */
public class Registry {

  //
  // CONSTANTS
  //
  // the port the registry is listening on
  public static final int REGISTRY_PORT = 1099; // 1099 = default value

  //
  // MAIN
  //
  public static synchronized void main(String[] args) throws Exception {

    System.out
            .println("registry: running on host " + InetAddress.getLocalHost());

    // create the registry on the local machine, on the default port number
    LocateRegistry.createRegistry(REGISTRY_PORT);
    System.out.println("registry: listening on port " + REGISTRY_PORT);

    // block forever
    Registry.class.wait();
    System.out.println("registry: exiting (should not happen)");

  }

}
