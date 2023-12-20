import java.util.List;

public class Account {
    private static int idCounter = 1;

    public int getId() {
        return id;
    }

    private int id;
    private int balance;
    private List<Operation> operationLog;

    public Account(int balance, List<Operation> operationLog) {
        this.balance = balance;
        this.operationLog = operationLog;
        this.id = idCounter;
        idCounter++;
    }

    public synchronized void deposit(int amount){
        balance += amount;
    }

    public synchronized void withdraw(int amount) throws Exception{
        if(balance >= amount)
            balance -= amount;
        else
            throw new Exception("Not enough funds");
    }
    public void addToLog(Operation operation){
        operationLog.add(operation);
    }

    public void check(){
        synchronized (this){
            int sum = 1000;
            for(Operation op: operationLog){
                if(op.getSource() == this){
                    sum -= op.getAmount();
                }
                else{
                    sum += op.getAmount();
                }
            }
            System.out.println("Account " +this.id + " has " + this.balance +" and expected " + sum);
        }
    }
}
