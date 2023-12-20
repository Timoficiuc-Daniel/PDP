package org.example;

import java.util.List;

public class Producer extends Thread{
    private final Buffer buffer;
    private List<Integer> vector1;
    private List<Integer> vector2;

    public Producer(Buffer buffer, List<Integer> vector1, List<Integer> vector2) {
        super();
        this.buffer = buffer;
        this.vector1 = vector1;
        this.vector2 = vector2;
    }

    @Override
    public void run(){
            for (int i = 0; i < vector1.size(); i++) {
                int val1 = vector1.get(i);
                int val2 = vector2.get(i);
                int result = val1 * val2;
                synchronized (buffer) {
                    try {
                        while (buffer.isFull()) {
                            buffer.wait();
                        }
                        buffer.put(result);
                        System.out.printf("Producer: Sending %d * %d = %d\n", val1, val2, val1 * val2);
                        buffer.notify();
//                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
}
