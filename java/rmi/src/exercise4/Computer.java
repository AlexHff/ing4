package exercise4;

/**
 * Interface of a generic computer server.
 *
 * The {@code execute} method takes as a parameter the {@link Task} to execute,
 * and returns the value output by the task.
 *
 */
public interface Computer {
    public <T> T execute(Task<T> task);
}
