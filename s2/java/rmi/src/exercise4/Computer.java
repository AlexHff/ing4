package exercise4;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface of a generic computer server.
 *
 * The {@code execute} method takes as a parameter the {@link Task} to execute,
 * and returns the value output by the task.
 *
 */
public interface Computer extends Remote {
    public <T> T execute(Task<T> task) throws RemoteException;
}
