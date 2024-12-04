package org.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingeltonThreadPool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i<5; i++)
            executorService.execute(new RunnableExample(i));
        executorService.shutdown();
    }
}
