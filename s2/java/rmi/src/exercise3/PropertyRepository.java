package exercise3;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface of a property repository.
 *
 */
public interface PropertyRepository extends Remote {

  public String getProperty(String key) throws RemoteException;

  public void setProperty(String key, String value) throws RemoteException;

  public void removeProperty(String key) throws RemoteException;

}
