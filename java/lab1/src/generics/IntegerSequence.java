package generics;

/**
 * A simplistic sequence of {@code Integer}s.
 *
 * @author Jean-Michel Busca
 *
 */
public class IntegerSequence {

  private final Integer[] elements;
  private int size;

  public IntegerSequence() {
    elements = new Integer[100];
    size = 0;
  }

  public void add(Integer element) {
    if (size >= elements.length) {
      throw new IllegalStateException();
    }
    elements[size] = element;
    size += 1;
  }

  public boolean remove(Integer element) {
    int i = 0;
    while (i < size && !elements[i].equals(element)) {
      i += 1;
    }
    if (i >= size) {
      return false;
    }
    size -= 1;
    while (i < size) {
      elements[i] = elements[i + 1];
    }
    return true;
  }

  public Integer get(int index) {
    if (index < 0 || index >= size) {
      throw new IllegalArgumentException();
    }
    return elements[index];
  }
}
