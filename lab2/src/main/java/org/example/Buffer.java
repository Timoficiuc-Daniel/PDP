package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
    private Queue<Integer> queue = new LinkedList<>();
    private boolean full=false;
    private boolean empty=true;
    private final int maxSize;

    public Buffer(int maxSize) {
        this.maxSize = maxSize;
    }

    synchronized public boolean isFull(){
        return full;
    }

    synchronized public boolean isEmpty(){
        return empty;
    }

    synchronized public int get(){
        if(queue.size() == 1){
            empty = true;
        }
        full = false;
        return queue.poll();
    }

    synchronized public void put(int value){
        if(queue.size() == maxSize-1){
            full=true;
        }
        empty=false;
        queue.add(value);
    }
}
