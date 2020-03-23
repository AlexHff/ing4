package exercise4.server;

import exercise4.Computer;
import exercise4.Task;

public class TaskManager implements Computer {
    @Override
    public <T> T execute(Task<T> task) {
        return task.execute();
    }
}