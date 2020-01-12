package lambdas;

/**
 * A string tester that tests whether a string starts with "A". This tester is
 * defined as a regular class implementing the StringTester interface.
 *
 * @author Jean-Michel Busca
 */
class StringStartsWithA implements StringTester {

  @Override
  public boolean test(String string) {
    return string.startsWith("A");
  }

}
