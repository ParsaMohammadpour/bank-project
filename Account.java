public class Account{
    private static int countOfAll =0;
    private int id;
    private int ownerCounter =0;
    private int[] ownerId;
    private int transactionCounter =0;
    private int balance;
    Transaction[]transaction;

    public Account(int[]ownerid_balance,int ownerCounter){
        this.id = countOfAll;
        countOfAll++;
        this.ownerCounter = ownerCounter;
        this.balance =ownerid_balance[ownerCounter];
        this.ownerId =new int[100];
        setOwnerid(ownerid_balance, ownerCounter);
        transaction=new Transaction[1000];
    }

    public int getId() {
        return id;
    }

    public int getTransactionCounter() {
        return transactionCounter;
    }

    public void addmoney(int mount){
        this.balance +=mount;
    }

    public void setOwnerid(int[] ownerid,int ownercounter) {
        for (int i = 0; i < 100; i++) {
            this.ownerId[i] =-1;
        }
        for (int i = 0; i < ownercounter; i++) {
            this.ownerId[i] =ownerid[i];
        }
    }

    public int addtransaction(int mount,int destid){
        transaction[transactionCounter] =new Transaction(ownerId,mount,destid, ownerCounter);
        transactionCounter++;
        return transaction[transactionCounter -1].getId();
    }

    public void checktransaction(){
        for (int i = 0; i < transactionCounter; i++) {
            transaction[i].checksituation(balance);
        }
        for (int i = 0; i < transactionCounter; i++) {
            if((transaction[i].situation.equals("accepted")) && (transaction[i].forAccepted == null)){
                balance-=transaction[i].getMount();
                transaction[i].forAccepted ="notdone";
            }
        }
    }

    public void addowner(int id){
        for (int i = 0; i < 100; i++) {
            if(ownerId[i] == -1){
                ownerId[i] =id;
                ownerCounter++;
                return;
            }
        }
    }

    public void showaccount(Person[]people, int personcounter){
        System.out.println("Balance : " + balance);
        System.out.print("Owners : ");
        for (int i = 0; i < ownerCounter - 1; i++) {
            for (int j = 0; j < personcounter; j++) {
                if(ownerId[i] == people[j].getId()){
                    System.out.print(people[j].name + " " + people[j].surname + " , ");
                    break;
                }
            }
        }
        for (int i = 0; i < personcounter; i++) {
            if(ownerId[ownerCounter - 1] == people[i].getId()){
                System.out.println(people[i].name + " " + people[i].surname);
                break;
            }
        }
        System.out.println("Transactions:");
        int line =1;
        for (int i = 0; i < transactionCounter; i++) {
            System.out.println("[" + line + "] " + transaction[i].getMount() + " to " + transaction[i].getDestId() + " -> "
                    + transaction[i].situation );
            line++;
        }
    }

    public int idofdest(){
        for (int i = 0; i < transactionCounter; i++) {
            if((transaction[i].situation.equals("accepted"))&&(transaction[i].forAccepted.equals("notdone"))){
                transaction[i].forAccepted ="done";
                return transaction[i].getDestId();
            }
        }
        return -1;
    }

    public boolean hastransaction(int transactionid){
        for (int i = 0; i < transactionCounter; i++) {
            if(transaction[i].getId() == transactionid){
                return true;
            }
        }
        return false;
    }

    public void approvetransaction(int transactionid,int ownerid){
        for (int i = 0; i < transactionCounter; i++) {
            if(transaction[i].getId() == transactionid){
                transaction[i].approve(ownerid);
                transaction[i].checksituation(this.balance);
            }
        }
        checktransaction();
    }

    public void declinetransaction(int transactionid,int ownerid){
        for (int i = 0; i < transactionCounter; i++) {
            if(transaction[i].getId() == transactionid){
                transaction[i].decline(ownerid);
                transaction[i].checksituation(this.balance);
            }
        }
        checktransaction();
    }
}