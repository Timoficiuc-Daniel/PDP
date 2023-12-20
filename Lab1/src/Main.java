public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank(10);
        bank.execute(100000,16);
        //testAll();
    }

    public static void testAll(){
        testOneThread();
        testTwoThreads();
        testEightThreads();
        testSixteenThreads();

        System.out.println("Testing more granular");
        testOneThreadMoreGranular();
        testTwoThreadsMoreGranular();
        testEightThreadsMoreGranular();
        testSixteenThreadsMoreGranular();
    }

    public static void testOneThread(){
        final long startTime = System.currentTimeMillis();
        Bank bank = new Bank(10);
        bank.execute(100000,1);
        final long stopTime = System.currentTimeMillis();
        System.out.println("Real time=" + (stopTime-startTime) + "ms");
    }

    public static void testTwoThreads(){
        final long startTime = System.currentTimeMillis();
        Bank bank = new Bank(10);
        bank.execute(100000,2);
        final long stopTime = System.currentTimeMillis();
        System.out.println("Real time=" + (stopTime-startTime) + "ms");
    }

    public static void testEightThreads(){
        final long startTime = System.currentTimeMillis();
        Bank bank = new Bank(10);
        bank.execute(100000,8);
        final long stopTime = System.currentTimeMillis();
        System.out.println("Real time=" + (stopTime-startTime) + "ms");
    }


    public static void testSixteenThreads(){
        final long startTime = System.currentTimeMillis();
        Bank bank = new Bank(10);
        bank.execute(100000,16);
        final long stopTime = System.currentTimeMillis();
        System.out.println("Real time=" + (stopTime-startTime) + "ms");
    }

    public static void testOneThreadMoreGranular(){
        final long startTime = System.currentTimeMillis();
        Bank bank = new Bank(10);
        bank.executeMoreGranular(100000,1);
        final long stopTime = System.currentTimeMillis();
        System.out.println("Real time=" + (stopTime-startTime) + "ms");
    }

    public static void testTwoThreadsMoreGranular(){
        final long startTime = System.currentTimeMillis();
        Bank bank = new Bank(10);
        bank.executeMoreGranular(100000,2);
        final long stopTime = System.currentTimeMillis();
        System.out.println("Real time=" + (stopTime-startTime) + "ms");
    }

    public static void testEightThreadsMoreGranular(){
        final long startTime = System.currentTimeMillis();
        Bank bank = new Bank(10);
        bank.executeMoreGranular(100000,8);
        final long stopTime = System.currentTimeMillis();
        System.out.println("Real time=" + (stopTime-startTime) + "ms");
    }


    public static void testSixteenThreadsMoreGranular(){
        final long startTime = System.currentTimeMillis();
        Bank bank = new Bank(10);
        bank.executeMoreGranular(100000,16);
        final long stopTime = System.currentTimeMillis();
        System.out.println("Real time=" + (stopTime-startTime) + "ms");
    }
}