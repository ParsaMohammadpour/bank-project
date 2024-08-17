import java.util.Scanner;

public class Main {

    public static void create_customer_order(Person[]people,int personCounter,String name,String surname){
        people[personCounter] =new Person(name,surname);
        System.out.println(people[personCounter].getId());
    }

    public static void create_account_order(Account[]accounts,int accountCounter,int[] ownerId_Balance,int numberOfOwner){
        accounts[accountCounter] =new Account(ownerId_Balance,numberOfOwner);
        System.out.println(accounts[accountCounter].getId());
    }

    public static void addowner(Account[]account,int accountCoounter,int ownerId,int accountId){
        for (int i = 0; i < accountCoounter; i++) {
            if(account[i].getId() == accountId){
                account[i].addowner(ownerId);
                return;
            }
        }
    }

    public static void boobook_transaction(Account[]accounts,int accountCounter,int accountId,int destId,int mount){
        for (int i = 0; i < accountCounter; i++) {
            if(accounts[i].getId() == accountId){
                int idoftransaction =accounts[i].addtransaction(mount,destId);
                System.out.println(idoftransaction);
                return;
            }
        }
    }

    public static void move_money(Account[]accounts,int accountCounter){
        for (int i = 0; i < accountCounter; i++) {
            for (int j = 0; j < accounts[i].getTransactionCounter(); j++) {
                if(((accounts[i].transaction[j].forAccepted != null) && (accounts[i].transaction[j].forAccepted.equals("notdone")))){
                    int destid =accounts[i].transaction[j].getDestId();
                    int mount =accounts[i].transaction[j].getMount();
                    for (int k = 0; k < accountCounter; k++) {
                        if(accounts[k].getId() == destid){
                            accounts[k].addmoney(mount);
                        }
                    }
                    accounts[i].transaction[j].forAccepted ="done";
                    return;
                }
            }
        }
    }

    public static void approve_transaction(Account[]accounts,int accountCounter,int transactionId,int ownerId){
        boolean hastransaction =false;
        for (int i = 0; i < accountCounter; i++) {
            hastransaction =accounts[i].hastransaction(transactionId);
            if(hastransaction){
                accounts[i].approvetransaction(transactionId,ownerId);
            }
        }

        move_money(accounts,accountCounter);
    }

    public static void decline_transaction(Account[]accounts,int accountCounter,int transactionId,int ownerId){
        boolean hastransaction =false;
        for (int i = 0; i < accountCounter; i++) {
            hastransaction =accounts[i].hastransaction(transactionId);
            if(hastransaction){
                accounts[i].declinetransaction(transactionId,ownerId);
            }
        }
    }

    public static void show_account_order(Account[]accounts,int accountNumber,Person[]people,int personCounter,int accountId){
        for (int i = 0; i < accountNumber; i++) {
            if(accounts[i].getId() == accountId){
                accounts[i].showaccount(people,personCounter);
                return;
            }
        }
    }

    public static void main(String[] args) {
        Person[]people =new Person[2000];
        Account[]accounts =new Account[3000];
        int personCounter=0;
        int accountCounter=0;
        int transactionNumber=0;
        String name;
        String surname;
        int ownerId;
        int accountId;
        int destId;
        int mount;
        int transactionId;
        int[] ownerId_Balance =new int[101];
        for (int i = 0; i < 101; i++) {
            ownerId_Balance[i]=-1;
        }
        int numberOfOwner=0;
        String order;
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            order =scanner.next();
            if(order.equals("create_customer")){
                name =scanner.next();
                surname =scanner.next();
                create_customer_order(people,personCounter,name,surname);
                personCounter++;
                continue;
            }
            if(order.equals("create_account")){
                while (scanner.hasNextInt()){
                    ownerId_Balance[numberOfOwner] =scanner.nextInt();
                    numberOfOwner++;
                }
                numberOfOwner--;
                create_account_order(accounts,accountCounter,ownerId_Balance,numberOfOwner);
                accountCounter++;
                numberOfOwner=0;
                for (int i = 0; i < 101; i++) {
                    ownerId_Balance[i] =-1;
                }
                continue;
            }
            if(order.equals("add_owner")){
                accountId =scanner.nextInt();
                ownerId =scanner.nextInt();
                addowner(accounts,accountCounter,ownerId,accountId);
                continue;
            }
            if(order.equals("book_transaction")){
                accountId =scanner.nextInt();
                destId =scanner.nextInt();
                mount =scanner.nextInt();
                boobook_transaction(accounts,accountCounter,accountId,destId,mount);
                transactionNumber++;
                continue;
            }
            if(order.equals("approve_transaction")){
                transactionId =scanner.nextInt();
                ownerId =scanner.nextInt();
                approve_transaction(accounts,accountCounter,transactionId,ownerId);
                continue;
            }
            if(order.equals("decline_transaction")){
                transactionId =scanner.nextInt();
                ownerId =scanner.nextInt();
                decline_transaction(accounts,accountCounter,transactionId,ownerId);
                continue;
            }
            if(order.equals("show_account")){
                accountId =scanner.nextInt();
                show_account_order(accounts,accountCounter,people,personCounter,accountId);
                continue;
            }
        }
    }
}
