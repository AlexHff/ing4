package exercise4.server;

import java.rmi.RemoteException;

import exercise4.Computer;
import exercise4.Task;

public class ComputerEngine implements Computer {

    public ComputerEngine() {
    }

    @Override
    public <T> T execute(Task<T> task) throws RemoteException {
        return task.execute();
    }
}