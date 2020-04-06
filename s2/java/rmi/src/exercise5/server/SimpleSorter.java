package exercise5.server;

import java.util.Collections;
import java.util.List;

import exercise5.Sorter;

/**
 * A simple implementation of {@link Sorter} using methods of class {@code Collections}. For test purposes, the
 * {@code toString()} method displays the name of the current thread.
 *
 * Note: methods {@code sort} and {@code reverseSort} do not throw {@code RemoteException}. This demonstrates that this
 * exception is not thrown by the server code, but rather by the RMI runtime when a communication failure is detected in
 * the object's stub, on the client side.
 *
 */
public class SimpleSorter implements Sorter {

  @Override
  public List<String> sort(List<String> list) {

    System.out.println(this + ": receveid " + list);

    Collections.sort(list);

    System.out.println(this + ": returning " + list);
    return list;
  }

  @Override
  public MyList reverseSort(List<String> list) {

    System.out.println(this + ": receveid " + list);

    Collections.sort(list);
    Collections.reverse(list);

    System.out.println(this + ": returning " + list);
    return new MyList(list);
  }

  @Override
  public String toString() {
    return "SimpleSorter " + Thread.currentThread();
  }

}
