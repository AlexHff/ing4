package exercise1;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The Sorter interface defines a service that <code>sort</code> and
 * <code>reverseSort</code> lists of <code>String</code>s.
 *
 * As a <b>remote</b> interface, Sorter must:
 * <ul>
 * <li>extends the <code>Remote</code> interface,
 * <li>have all its methods throw <code>RemoteException</code>.
 * </ul>
 *
 */
public interface Sorter extends Remote {

  public List<String> sort(List<String> list) throws RemoteException;

  public List<String> reverseSort(List<String> list) throws RemoteException;

}
