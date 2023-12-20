import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bank {
    List<Account> accountList;

    public Bank(int nrAcc) {
        accountList = new ArrayList<Account>();
        for(int i = 0; i<nrAcc; i++){
            accountList.add(new Account(1000,new ArrayList<Operation>()));
        }
    }

    public void execute(int iter, int nrThreads){
        Thread[] threads = new Thread[nrThreads];
        for (int i = 0; i < nrThreads; ++i)
        {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0;i<iter/nrThreads;i++){
                        Random random = new Random();
                        int source = random.nextInt(accountList.size());
                        int dest;
                        do{
                            dest = random.nextInt(accountList.size());
                        }while (source == dest);
                        int amount = random.nextInt(100)+1;
                        Operation transfer = new Operation(accountList.get(source),accountList.get(dest),amount);
                        transfer.execute();

                        if(random.nextInt(10) == 1){
                            accountList.get(source).check();
                            accountList.get(dest).check();
                        }
                    }
                }
            });
        }
        for(int i = 0; i < nrThreads; i++){
           threads[i].start();
        }
        for (int i = 0; i < nrThreads; ++i)
        {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(Account acc: accountList){
            acc.check();
        }
    }

    public void executeMoreGranular(int iter, int nrThreads){
        Thread[] threads = new Thread[nrThreads];
        for (int i = 0; i < nrThreads; ++i)
        {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0;i<iter/nrThreads;i++){
                        Random random = new Random();
                        int source = random.nextInt(accountList.size());
                        int dest;
                        do{
                            dest = random.nextInt(accountList.size());
                        }while (source == dest);
                        int amount = random.nextInt(100)+1;
                        Operation transfer = new Operation(accountList.get(source),accountList.get(dest),amount);
                        transfer.execute();

                        if(random.nextInt(10) == 1){
                            accountList.get(source).check();
                            accountList.get(dest).check();
                        }
                    }
                }
            });
        }
        for(int i = 0; i < nrThreads; i++){
            threads[i].start();
        }
        for (int i = 0; i < nrThreads; ++i)
        {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(Account acc: accountList){
            acc.check();
        }
    }
}
