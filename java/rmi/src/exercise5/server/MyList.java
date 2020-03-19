package exercise5.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Subclass of <code>ArrayList</code> with no additional functionality. The sole
 * purpose of the class is to define a class that is known to the client but not
 * to the server (or vice-versa).
 *
 */
public class MyList extends ArrayList<String> {

  private static final long serialVersionUID = 1L;

  public MyList(List<String> l) {
    super(l);
  }

  @Override
  public String toString() {
    return "MyList: " + super.toString();
  }

}
