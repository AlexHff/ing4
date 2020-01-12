package lambdas;

/**
 * Demonstrates the use of anonymous classes, lambda expressions and method
 * references.
 *
 * @author Jean-Michel Busca
 */
public class Main {

    /**
     * Checks whether the specified strings all pass the specified test.
     *
     * @param strings the strings to check
     * @param tester  the string tester to use
     *
     * @return true if the specified strings all pass the test, and false otherwise
     */
    private static boolean checkStrings(String[] strings, StringTester tester) {
        // go through all the specified strings
        for (String string : strings) {
            // test the current string using the specefied tester object
            if (!tester.test(string)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        // the strings we want to test
        String[] strings = { "123", "abc", "you" };
        //String[] strings = { "foo", "foo", "foo" };

        // Test #1: do all string start with "A"?
        // We use an instance of the StringStartsWithA class as the tester object
        boolean ok1 = checkStrings(strings, new StringStartsWithA());
        System.out.println("all strings starts with \"a\": " + ok1);

        // Test #2: are all strings 3-character long?
        // We use an instance of an anonymous class as the tester object
        boolean ok2 = checkStrings(strings, new StringTester() {
            @Override
            public boolean test(String string) {
                return string.length() == 3;
            }
        });
        System.out.println("all strings are 3-character long: " + ok2);

        // Test #3: are all strings equal to "foo"?
        // Your turn: use a lambda expression to define the tester
        boolean ok3 = checkStrings(strings, (String s) -> s.equals("foo"));
        System.out.println("all string are equal to foo: " + ok3);

        // Test #4: are all strings equal to "foo"? (same test as above)
        // Your turn: use method reference to define the tester
        boolean ok4 = checkStrings(strings, new String("foo")::equals);
        System.out.println("all string are equal to foo: " + ok4);
    }
}
