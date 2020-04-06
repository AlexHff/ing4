package generics;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * GenericsSequence
 */
public class GenericsSequence<T extends Comparable<T>> {
    private final static int LEN = 100;
    private T[] seq;
    private int size;

    @SuppressWarnings("unchecked")
    public GenericsSequence(Class<T> componentType) {
        seq = (T[]) Array.newInstance(componentType, LEN);
        size = 0;
    }

    public T max() {
        T max = seq[0];
        for (T elem : seq)
            if (max != null && elem != null && elem.compareTo(max) < 0)
                max = elem;
        return max;
    }

    public void add(T elem) {
        if (size >= seq.length)
            throw new IllegalStateException();
        seq[size++] = elem;
    }

    public boolean remove(T elem) {
        int i = 0;
        while (i < size && !seq[i].equals(elem))
            i++;
        if (i >= size)
            return false;
        size--;
        while (i < size)
            seq[i] = seq[++i];
        return true;
    }

    public boolean remove(List<T> list) {
        for (T elem : list)
            if (!remove(elem))
                return false;
        return true;
    }

    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException();
        return seq[index];
    }

    @Override
    public String toString() {
        return "GenericsSequence [seq=" + Arrays.toString(seq)
                + ", size=" + size + "]";
    }
}