package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Integer> vector1 = new ArrayList<>();
        ArrayList<Integer> vector2 = new ArrayList<>();
        Random rand = new Random();
        int vectorSize = 100;
        int finalAnswer = 0;
        for(int i = 0; i < vectorSize; i++){
            vector1.add(rand.nextInt(10)+1);
            vector2.add(rand.nextInt(10)+1);
            finalAnswer += vector1.get(i)*vector2.get(i);
        }
        System.out.println("Final answer should be " + finalAnswer);
        int bufferSize = 4;
        Buffer buffer = new Buffer(bufferSize);
        Producer producer = new Producer(buffer,vector1,vector2);
        Consumer consumer = new Consumer(buffer,vectorSize);
        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}