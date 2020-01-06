import generics.GenericsSequence;

/**
 * App
 */
public class App {
    public static void main(String[] args) {
        GenericsSequence<String> g = new GenericsSequence<>(String.class);
        g.add("soso le null");
        g.add("aaa");
        g.add("bob");
        System.out.println(g.max());
        g.remove("aaa");
        System.out.println(g.max());
    }
}