package org.example;

public class Consumer extends Thread{
    private final Buffer buffer;
    private int sum=0;
    private int vectorSize;

    public Consumer(Buffer buffer, int vectorSize) {
        super();
        this.buffer = buffer;
        this.vectorSize = vectorSize;
    }
    @Override
    public void run(){
            for (int i = 0; i < vectorSize; i++) {
                int result;
                try {
                    synchronized (buffer) {
                        while (buffer.isEmpty()) {
                            buffer.wait();
                        }
                        result = buffer.get();
                        buffer.notify();
                    }
                    sum+=result;
                    System.out.printf("Consumer: sum is %d\n", sum);
//                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        System.out.printf("The final sum is: %d", sum);
    }
}
