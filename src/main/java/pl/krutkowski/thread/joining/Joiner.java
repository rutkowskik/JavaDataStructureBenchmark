package org.example.thread.joining;

public class Joiner extends Thread{
    private Sleeper sleeper;
    public Joiner(String name, Sleeper sleeper){
        super(name);
        this.sleeper = sleeper;
        start();
    }
    public void run(){
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println("Przerwano");
        }
        System.out.println(getName() + " zakończył połączenie");
    }
}
