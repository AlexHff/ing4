package annotations;

import annotations.definition.TestSpec;

/**
 * @author Jean-Michel Busca
 */
public class TestClass {
    @TestSpec(order = 1)
    public void foo() {
        System.out.println("foo");
    }

    @TestSpec(order = 2)
    public void bar() {
        System.out.println("bar");
    }

    @TestSpec(order = 3)
    public void baz() {
        System.out.println("baz");
    }
}
