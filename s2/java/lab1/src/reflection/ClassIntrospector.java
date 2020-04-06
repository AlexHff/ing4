package reflection;

import annotations.TestClass;

import java.lang.reflect.*;

/**
 * @author Jean-Michel Busca
 */
public class ClassIntrospector {
    public static <T> void printMethods(Class<T> clazz) {
        System.out.println(clazz.getName());
        Method m[] = clazz.getDeclaredMethods();
        for (Method method : m) {
            System.out.println(method.getName());
            Class<?>[] params = method.getParameterTypes();
            for (Class<?> p : params)
                System.out.println("    " + p);
        }
    }

    public static void main(String[] args) {
        printMethods(TestClass.class);
        printMethods(Object.class);
    }
}
