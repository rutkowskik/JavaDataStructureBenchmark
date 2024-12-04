package org.example.thread;

public class MoreBasicThread {

    public static void main(String[] args) {
        for (int i = 0; i<5; i++)
            new Thread(new RunnableExample(i)).start();
        System.out.println("Waiting for start");
    }
}
