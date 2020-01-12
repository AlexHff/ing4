package lambdas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

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

    /**
     * Checks whether all elements in the array pass the given predicate
     * @param <T> - type of the elements in the array
     * @param items - array containing elements to be checked
     * @param tester - a predicate which returns tests elements
     * @return true if all elements pass the tester
     * @since 1.8
     */
    private static <T> boolean checkItems(T[] items, Predicate<T> tester) {
        for (T item : items)
            if (!tester.test(item))
                return false;
        return true;
    }

    public static void main(String[] args) {
        // the strings we want to test
        String[] strings = { "youuu", "abcd", "123", "1233" };
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

        // Test #5: do all strings start with an "A"?
        boolean ok5 = checkItems(strings, new String("A")::startsWith);
        System.out.println("all strings starts with \"a\": " + ok5);

        // Test #6: are all strings 3 characters long?
        boolean ok6 = checkItems(strings, (String s) -> s.length() == 3);
        System.out.println("all strings are 3-character long: " + ok6);

        // Test #7: are all strings equal to "foo"?
        boolean ok7 = checkItems(strings, (String s) -> s.equals("foo"));
        System.out.println("all string are equal to foo: " + ok7);

        // Test #8: are all strings equal to "foo"? (same test as above)
        boolean ok8 = checkItems(strings, new String("foo")::equals);
        System.out.println("all string are equal to foo: " + ok8);

        Integer[] numbers = {5,9,3,1,4,5};

        // Test #9: are all integers between 0 and 10 (excluded)?
        boolean ok9 = checkItems(numbers, (Integer n) -> n >= 0 && n < 10);
        System.out.println("all integers are between 0 and 10 (excluded): " + ok9);

        // Test #10: are all integers multiples of 2?
        boolean ok10 = checkItems(numbers, (Integer n) -> n % 2 == 0);
        System.out.println("all integers are multiples of 2: " + ok10);

        List<Integer> list = new ArrayList<>();
        for (Integer n : numbers)
            list.add(n);
        list.forEach(e -> System.out.println(e));

        Comparator<String> stringLenComparator = Comparator.comparing(String::length);
        Arrays.sort(strings, stringLenComparator);
        System.out.println(Arrays.toString(strings));

        Comparator<String> revStringLenComparator = stringLenComparator.reversed();
        Arrays.sort(strings, revStringLenComparator);
        System.out.println(Arrays.toString(strings));
        
        Comparator<String> lexStringComparator = Comparator.naturalOrder();
        Comparator<String> sComparator = lexStringComparator.thenComparing(revStringLenComparator);
        Arrays.sort(strings, sComparator);
        System.out.println(Arrays.toString(strings));
    }
}
