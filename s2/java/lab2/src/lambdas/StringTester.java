package lambdas;

/**
 * Interface that defines only one abstract method: test().
 *
 * @author Jean-Michel Busca
 */
@FunctionalInterface
public interface StringTester {

    /**
     * Test the specified string for some property.
     *
     * @param string the string to test
     *
     * @return true if the specified string passes the test and false otherwise.
     */
    boolean test(String string);
}
