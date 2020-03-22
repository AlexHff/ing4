package exercise4;

/**
 * Interface of a generic task.
 *
 * The {@code execute} method executes the task and returns its result.
 *
 */
public interface Task<T> {
    public T execute();
}
