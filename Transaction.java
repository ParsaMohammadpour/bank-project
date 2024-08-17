public class Transaction{
    private static int countOfAll =0;
    private int id;
    private int mount;
    private int[] ownersId;
    private int[] ownersSituation;//    0 =nothing      1=accepted       -1=rejected
    String situation;
    String forAccepted;
    private int destId;

    public Transaction(int[] ownersId, int mount, int destId, int numberOfOwner){
        this.id = countOfAll;
        countOfAll++;
        this.mount=mount;
        this.destId = destId;
        this.ownersId =new int[numberOfOwner];
        this.ownersSituation =new int[numberOfOwner];
        for (int i = 0; i < numberOfOwner; i++) {
            this.ownersId[i] = ownersId[i];
            this.ownersSituation[i] =0;
        }
        situation ="pending";
    }

    public int getId() {
        return id;
    }

    public int getDestId() {
        return destId;
    }

    public int getMount() {
        return mount;
    }

    public void approve(int ownerid){
        for (int i = 0; i < ownersId.length; i++) {
            if(ownersId[i] == ownerid){
                ownersSituation[i] =1;
                return;
            }
        }
    }

    public void decline(int ownerid){
        for (int i = 0; i < ownersSituation.length; i++) {
            if(ownersId[i] == ownerid){
                ownersSituation[i] =-1;
                situation ="rejected";
                return;
            }
        }
    }

    public void checksituation(int balance){
        if(situation.equals("rejected")){
            return;
        }
        if(situation.equals("accepted")){
            return;
        }
        for (int i = 0; i < ownersSituation.length; i++) {
            if(ownersSituation[i] == 0){
                situation ="pending";
                return;
            }
        }
        if(this.mount <= balance){
            situation ="accepted";
            return;
        }
        else{
            situation ="rejected";
        }
    }
}