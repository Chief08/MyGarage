import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // initialize variables
        final int carslots = 10;
        final int bikeslots = 5;
        List<Vehicle> cars = new ArrayList<>();
        List<Vehicle> bikes = new ArrayList<>();
        List<Staff> staff = new ArrayList<>();
        initializeStaff(staff);
        double profit = 0;
        int action = 0;
        Scanner scan = new Scanner(System.in);

        // main loop
        while (true) {
            pressEnterToContinue();
            menu();
            String temp = scan.next();
            if(isValid(temp)){
                action = Integer.valueOf(temp);
            } else{
                System.out.println("Please insert valid number");
                action = 10;
            }

            switch (action) {
                case 1:
                    insert(cars,bikes,carslots,bikeslots);
                    break;
                case 2:
                    System.out.println("Enter the plate of the vehicle you want to remove");
                    String temp0 = scan.next();
                    profit += remove(temp0,cars,bikes);
                    break;
                case 3:
                    System.out.println("Your current profit is " + profit);
                    break;
                case 4:
                    System.out.println("Enter the plate you want to search");
                    String temp1 = scan.next();
                    findstaff(temp1, cars, bikes, staff);
                    break;
                default:
                    break;
            }

            if(action == 0){
                System.out.println("Your final profit is " + profit);
                break;
            }

        }


    }



    public static void insert(List<Vehicle> cars, List<Vehicle> bikes, int carslots, int bikeslots) {
        int action = -1;
        String plate = "";
        String owner = "";
        int parkedBy = 0;
        String vechiletype = "";
        myDate time0 = new myDate();
        boolean discount = false;
        Scanner scan = new Scanner(System.in);

        //type scan
        while (action == -1) {
            System.out.println("Press 1 for car, 2 motorcycle or 0 to exit");
            String temp = scan.next();
            if (temp != null && temp.matches("[0-2]+")) {
                action = Integer.valueOf(temp);
            } else {
                System.out.println("Please insert valid number");
                action = -1;
            }
        }
        switch (action) {
            case 0:
                return;
            case 1:
                if (cars.size() == carslots) {
                    System.out.println("Sorry no more car slots");
                    return;
                }
                vechiletype = "car";
                break;
            case 2:
                if (bikes.size() == bikeslots) {
                    System.out.println("Sorry no more bike slots");
                    return;
                }
                vechiletype = "motorcycle";
                break;
            default:
                break;
        }

        //plate scan
        action = 0;
        while (action == 0){
            System.out.println("Type the plate number");
            String temp = scan.next();
            if(temp != null && temp.matches("[0-9A-Z]+") && temp.length()<8){ //need more conditions but I'm not
                plate = temp;                                                     //sure of the plates official format
                action = 1;
                for(Vehicle x : cars){
                    if(x.plate.equals(plate)){
                        System.out.println("This plate is already in the parking");
                        return;
                    }
                }
                for(Vehicle x : bikes){
                    if(x.plate.equals(plate)){
                        System.out.print("This plate is already in the parking");
                        return;
                    }
                }


            } else{
                System.out.println("Please insert a valid plate");
            }
        }

        // owner scan
        action = 0;
        while(action == 0){
            System.out.println("Type the customer's name");
            String temp = scan.next();
            if(isAlpha(temp)){
                owner=temp;
                action = 1;
            }else {
                System.out.println("The name should contain only letters,apostrophes and whitespace");
            }
        }

        // date scan
        time0 = dateScan();


        // staff scan
        Random r = new Random();//I don't know how they decide who will do the parking. I could print the id.
        parkedBy = r.nextInt(4)+1; //The bound should be staff.size() but size is fixed.


        //review
        while (true) {
            System.out.println("Vehicle type: " + vechiletype);
            System.out.println("Vehicle plate: " + plate);
            System.out.println("Vehicle driver: " + owner);
            time0.print();
            System.out.println("If the information above are correct press 1 or press 0 to exit");
            String temp = scan.next();
            if (temp.equals("1")) {
                for(int x=0; x<cars.size(); x++){
                    if(cars.get(x).owner.equals(owner)){ //I take as granted that names are unique and no customer
                        Vehicle temp1 = cars.get(x);       //class with ID and details have to be made.
                        temp1.discount = true;
                        cars.set(x, temp1);
                        discount = true;
                    }
                }
                for(int x=0; x<bikes.size(); x++){
                    if(bikes.get(x).owner.equals(owner)){
                        Vehicle temp1 = bikes.get(x);
                        temp1.discount = true;
                        bikes.set(x, temp1);
                        discount = true;
                    }
                }
                if(vechiletype.equals("car")){
                    System.out.println("Car has been inserted successfully");
                    cars.add(new Vehicle(plate, owner, parkedBy, vechiletype, time0, discount));
                }else{
                    System.out.println("Motorcycle has been inserted successfully");
                    bikes.add(new Vehicle(plate, owner, parkedBy, vechiletype, time0, discount));
                }
                return;

            } else if (temp.equals("0")) {
                System.out.println("Exit");
                return;
            }else {
                System.out.println("Type a valid answer");
            }

        }
    }


    public static double remove(String plate, List<Vehicle> cars, List<Vehicle> bikes){
        Scanner scan = new Scanner(System.in);
        boolean flag = true;
        double cost = 0;

        while (flag){
            System.out.println("For a car press 1 ,for a motorcycle press 2, to exit press 0");
            String temp = scan.next();
            if(temp.equals("1")){
                for(int x=0; x<cars.size(); x++){
                    if(cars.get(x).plate.equals(plate)){
                        myDate exitdatetime = dateScan();
                        if(normalDates(cars.get(x).time0, exitdatetime)){
                            cost = calculateCost(cars.get(x),exitdatetime,"car");
                            System.out.println("Cost is : "+cost);
                            boolean a =cars.remove(cars.get(x));
                            System.out.println("Car has been removed successfully");
                            return cost;
                        } else {
                            System.out.println("Wrong date was given");
                            return 0;
                        }
                    }
                }

                System.out.println("There is no car with this plate");
                return 0;
            } else if (temp.equals("2")) {
                for(int x=0; x<bikes.size(); x++){
                    if(bikes.get(x).plate.equals(plate)){
                        myDate exitdatetime = dateScan();
                        if(normalDates(bikes.get(x).time0, exitdatetime)){
                            cost = calculateCost(bikes.get(x),exitdatetime,"bike");
                            System.out.println("Cost is : "+cost);
                            boolean a =bikes.remove(bikes.get(x));
                            System.out.println("Motorcycle has been removed successfully");
                            return cost;
                        } else {
                            System.out.println("Wrong date was given");
                            return 0;
                        }
                    }
                }

                System.out.println("There is no motorcycle with this plate");
                return 0;
            } else if (temp.equals("0")) {
                System.out.println("Exit");
                return 0;
            }else {
                System.out.println("Please press a valid number");
            }
        }
        return cost;

    }

     public static void findstaff(String plate, List<Vehicle> cars, List<Vehicle> bikes, List<Staff> staff){
        Scanner scan = new Scanner(System.in);
        boolean flag = true;
        while (flag){
            System.out.println("For a car press 1 ,for a motorcycle press 2, to exit press 0");
            String temp = scan.next();
            if(temp.equals("1")){
                for(Vehicle x : cars){
                    if(x.plate.equals(plate)){
                        for(Staff s : staff){
                            if(x.parkedBy == s.personalId){
                                System.out.println("This car was parked by: ");
                                System.out.println("Name: "+s.name);
                                System.out.println("Phone number: "+s.phone);
                                System.out.println("Working ID: "+s.personalId);
                                return;
                            }
                        }
                    }
                }

                System.out.println("There is no car with this plate");
            } else if (temp.equals("2")) {
                for(Vehicle x : bikes){
                    if(x.plate.equals(plate)){
                        for(Staff s : staff){
                            if(x.parkedBy == s.personalId){
                                System.out.println("This car was parked by: ");
                                System.out.println("Name: "+s.name);
                                System.out.println("Phone number: "+s.phone);
                                System.out.println("Working ID: "+s.personalId);
                                return;
                            }
                        }
                    }
                }

                System.out.println("There is no motorcycle with this plate");
            } else if (temp.equals("0")) {
                System.out.println("Exit");
                return;
            }else {
                System.out.println("Please press a valid number");
            }
        }
    }

    private static boolean isValid(String str){
        return str != null && str.matches("[0-4]+");
    }
    private static boolean isAlpha(String name) {
        return name != null && name.matches("[a-z 'A-Z]+");
    }
    private static boolean isNum(String number) {
        return number != null && number.matches("[0-9]+");
    }
    private static boolean isValidDate(int year,int month,int date){
        // I don't check all cases because in real life we take input from the cpu and not manually.
        return year>2000 && year<2050 && month>0 && month<13 && date>0 && date<31;
    }
    private static boolean isValidTime(int minutes,int hours){
        return minutes>=0 && minutes<60 && hours>=0 && hours<24;
    }

    private static boolean normalDates(myDate entry, myDate exit){
        Timestamp x = new Timestamp(entry.year, entry.month, entry.day, entry.hour, entry.minute, 0,0);
        Timestamp y = new Timestamp(exit.year, exit.month, exit.day, exit.hour, exit.minute, 0,0);

        double diffHours = (y.getTime()-x.getTime()) / (60 * 60 * 1000);// convert milliseconds to hours
        return diffHours>=0;
    }

    public static Double calculateCost(Vehicle vehicle, myDate exit, String type){
        double cost = 0.0;
        double multiplier = 2.0;
        myDate entry = vehicle.time0;
        if(type.equals("car")){
            multiplier = 3.0;
        }
        Timestamp x = new Timestamp(entry.year, entry.month, entry.day, entry.hour, entry.minute, 0,0);
        Timestamp y = new Timestamp(exit.year, exit.month, exit.day, exit.hour, exit.minute, 0,0);

        double diffHours = (y.getTime()-x.getTime()) / (60 * 60 * 1000);

        if(vehicle.discount){
            diffHours = diffHours -0.5;
            diffHours = Math.ceil(diffHours);
            diffHours = diffHours * 0.7;
        }
        if(!vehicle.discount){
            diffHours = Math.ceil(diffHours); // I charge per hour so 1 hour and 1 min == 2 hours
        }

        cost = diffHours * multiplier;
        cost = Math.round(cost * 100.0) / 100.0;
        return cost;
    }


    public static myDate dateScan(){
        int action = 0;
        Scanner scan = new Scanner(System.in);
        myDate time0 = new myDate();

        System.out.print("Please Type the current date and time"); //we could get date auto from computer
        while(action==0){                                          //but no use for this project
            System.out.println("Type the date in DD/MM/YYYY format");
            boolean flag=true;
            String temp = scan.next();
            String[] arr = temp.split("/");
            for(String s : arr){
                if(!isNum(s)){
                    flag=false;
                }
            }
            if(flag && arr.length==3){
                flag=isValidDate(Integer.valueOf(arr[2]), Integer.valueOf(arr[1]),Integer.valueOf(arr[0]));
                if(flag){
                    time0.day = Integer.valueOf(arr[0]);
                    time0.month = Integer.valueOf(arr[1]);
                    time0.year = Integer.valueOf(arr[2]);
                    action = 1;
                }
            }else{
                System.out.println("Type a valid date");
            }

        }

        action = 0;
        System.out.print("Please Type the current time"); //we could get date auto from computer
        while(action==0){                                 //but no use for this project
            System.out.println("Type the time in HH:MM format");
            boolean flag=true;
            String temp = scan.next();
            String[] arr = temp.split(":");
            for(String s : arr){
                if(!isNum(s)){
                    flag=false;
                }
            }
            if(flag && arr.length==2){
                flag=isValidTime(Integer.valueOf(arr[1]),Integer.valueOf(arr[0]));
                if(flag){
                    time0.hour = Integer.valueOf(arr[0]);
                    time0.minute = Integer.valueOf(arr[1]);
                    action = 1;
                }
            }else{
                System.out.println("Type a valid time");
            }

        }
        return time0;
    }

    public static void menu (){
        System.out.println("Press the correct key for each action:");
        System.out.println("1  to insert a new vehicle");
        System.out.println("2  to remove a vehicle");
        System.out.println("3  to see your current profit");
        System.out.println("4  to see who parked a vehicle");
        System.out.println("0  to exit");
    }

    private static void pressEnterToContinue()
    {
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {}
    }

    private static void initializeStaff(List<Staff> s){
        s.add(new Staff("Bob","6944444444"));
        s.add(new Staff("Tom","6954444444"));
        s.add(new Staff("Bill","6964444444"));
        s.add(new Staff("Will","6974444444"));
        //they get an ascending int as id
    }

}