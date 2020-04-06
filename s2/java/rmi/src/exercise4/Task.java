package exercise4;

import java.io.Serializable;

/**
 * Interface of a generic task.
 *
 * The {@code execute} method executes the task and returns its result.
 *
 */
public interface Task<T> extends Serializable {
    public T execute();
}
