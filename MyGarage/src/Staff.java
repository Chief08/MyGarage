public class Staff {
    static int id=1;
    int personalId;
    String name;
    String phone;

    Staff(String name, String phone ){
        this.personalId = id;
        id +=1;
        this.name = name;
        this.phone = phone;
    }
    public void print(){
        System.out.println(this.name+' '+this.phone+' '+this.personalId);
    }

}
