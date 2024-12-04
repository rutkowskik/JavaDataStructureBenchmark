package org.example.thread;

public class RunnableExample implements Runnable {

    protected int countDown = 15; //example counter value
    private int taskId;
    public RunnableExample() {
    }

    public RunnableExample(int taskId) {
        this.taskId = taskId;
    }

    public String status(){
        return "### " + taskId + "(" +
                (countDown > 0 ? countDown : "Stop!") + "), ";
    }
    @Override
    public void run() {
        while(countDown-- >0){
            System.out.print(status());
            Thread.yield();
        }
    }
}
