public class Operation {
    private final int id;
    private static int serialCounter = 1;
    private final Account source;
    private final Account destination;
    private final int amount;

    public Operation( Account source, Account destination, int amount) {
        this.id = serialCounter;
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        incSerialCounter();
    }

    public void execute(){
        if( source.getId() < destination.getId()) {
            synchronized (source) {
                synchronized (destination) {
                    try {
                        source.withdraw(amount);
                        destination.deposit(amount);
                        source.addToLog(this);
                        destination.addToLog(this);
                    } catch (Exception ignored) {
                    }
                }
            }}
        else{
            synchronized (destination) {
                synchronized (source) {
                    try {
                        source.withdraw(amount);
                        destination.deposit(amount);
                        source.addToLog(this);
                        destination.addToLog(this);
                    } catch (Exception ignored) {
                    }
                }
            }}
        }

    public void executeMoreGranular(){
        if(source.getId() < destination.getId()) {
            synchronized (source) {
                synchronized (destination) {
                    try {
                        source.withdraw(amount);
                        destination.deposit(amount);
                    } catch (Exception ignored) {
                    }
                }
            }
            source.addToLog(this);
            destination.addToLog(this);}
        else{
            synchronized (destination) {
                synchronized (source) {
                    try {
                        source.withdraw(amount);
                        destination.deposit(amount);
                    } catch (Exception ignored) {
                    }
                }
            }
            source.addToLog(this);
            destination.addToLog(this);}
    }

    private static void incSerialCounter(){
        serialCounter++;
    }

    public Account getSource() {
        return source;
    }
    public int getAmount() {
        return amount;
    }
}
