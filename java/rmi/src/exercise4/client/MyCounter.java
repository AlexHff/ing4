package exercise4.client;

import exercise4.Task;

public class MyCounter implements Task<Integer> {
    @Override
    public Integer execute() {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += i;
        }
        return sum;
    }
}