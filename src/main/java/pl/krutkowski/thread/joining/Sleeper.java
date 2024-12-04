package org.example.thread.joining;

public class Sleeper extends Thread{
    private int duration;
    public Sleeper(String name, int duration) {
        super(name);
        this.duration = duration;
        start();
    }
    public void run() {
        try{
            sleep(duration);
        } catch (InterruptedException e){
            System.out.println(getName() + " został przerwany. " +
                    "isInterrupted(): " + isInterrupted());
            return;
        }
        System.out.println(getName() + " został wybudzony");
    }
}
