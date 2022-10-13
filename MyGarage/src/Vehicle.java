

public class Vehicle {
    String plate;
    String owner;
    int parkedBy;
    String vechiletype;
    myDate time0;
    boolean discount;
    Vehicle(){
        this.parkedBy = 0;
        this.plate = "";
        this.owner = "";
        this.vechiletype = "";
        this.time0 = new myDate();
        this.discount = false;
    }

    Vehicle(String plate, String owner, int parkedBy, String vechiletype, myDate time0, boolean discount){
        this.parkedBy = parkedBy;
        this.plate = plate;
        this.owner = owner;
        this.vechiletype = vechiletype;
        this.time0 = time0;
        this.discount = discount;
    }
    public void print(){
        String output = this.plate +' '+this.owner+' '+this.discount+' '+this.parkedBy;
        System.out.println(output);
    }
}
