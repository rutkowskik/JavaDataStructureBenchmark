package org.example.thread;

public class BasicTreads {

    public static void main(String[] args) {
        Thread thread = new Thread(new RunnableExample(1));
        thread.start();
    }
}
