package org.example.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AttemptLocking {
    private ReentrantLock lock = new ReentrantLock();
    public void untimed() {
        boolean captured = lock.tryLock();
        try {
            System.out.println("tryLock(): " + captured);
        } finally {
            if(captured)
                lock.unlock();
        }
    }
    public void timed() {
        boolean captured = false;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println("tryLock(2, TimeUnit.SECONDS): " + captured);
        } finally {
            if (captured)
                lock.unlock();
        }
    }

    public static void main(String[] args) {
        final AttemptLocking al = new AttemptLocking();
        al.untimed(); //lock enable
        al.timed(); //lock disable
        //new task for lock
        new Thread() {
            {setDaemon(true);}
            public void run(){
                al.lock.lock();
                System.out.println("Locked");
            }
        }.start();
        Thread.yield();
        al.untimed();
        al.timed();
    }
}
