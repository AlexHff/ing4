package exercise4.client;

import exercise4.Task;

public class MyHello implements Task<String> {
    @Override
    public String execute() {
        return "Hello world!";
    }
}