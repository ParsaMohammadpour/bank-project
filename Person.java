public class Person{
    String name;
    String surname;
    private int id;
    private static int counter=0;

    public Person(String name,String surname){
        id =counter;
        this.counter++;
        this.name =name;
        this.surname =surname;
    }

    public int getId() {
        return id;
    }
}