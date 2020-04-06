package exercise4.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import exercise4.Computer;
import exercise4.Task;

public class Client {
    private static final String SERVICE_HOST = "localhost";
    private static final int REGISTRY_PORT = 1099;

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry(SERVICE_HOST, REGISTRY_PORT);
        System.out.println("client: retrieved registry");

        Computer engine = (Computer) registry.lookup("Computer");
        Task<Integer> counterTask = new Task<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Integer execute() {
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    sum += i;
                }
                return sum;
            }
        };
        Task<String> printTask = new Task<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public String execute() {
                return "Hello World!";
            }
        };
        System.out.println("client: retrieved stub");
        System.out.println(engine.execute(counterTask));
        System.out.println(engine.execute(printTask));

        System.out.println("client: exiting");
    }
}
