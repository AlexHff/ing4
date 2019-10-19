package test;

import model.DataAccess;

/**
 *
 * @author Jean-Michel Busca
 */
public class Test {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws Exception {
    // create a data access object
    DataAccess data
        = new DataAccess(args[0], args[1], args.length < 3 ? "" : args[2]);

    // access the database using high-level Java methods
    // ...
    // close the data access object when done
    // ...
  }
}
