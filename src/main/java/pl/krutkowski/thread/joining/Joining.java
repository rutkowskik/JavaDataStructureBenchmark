package org.example.thread.joining;

public class Joining {
    public static void main(String[] args) {
        Sleeper
                s1 = new Sleeper("S1", 1500),
                s2 = new Sleeper("S2", 1500);
        Joiner
                j =  new Joiner("J1", s1),
                j2 = new Joiner("J2", s2);
        s2.interrupt();
    }
}
