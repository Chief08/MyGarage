public class myDate {
    int year;
    int month;
    int day;
    int hour;
    int minute;

    myDate(){
        year = 0;
        month = 0;
        day = 0;
        hour = 0;
        minute = 0;
    }

    myDate(int year, int month, int day, int hour, int minute){
        this.year = year;
        this.month = month%12 + 1;
        this.day = day%31 + 1;
        this.hour = hour%24;
        this.minute = minute%60;
    }

    public void print(){
        String output = Integer.toString(this.day) +'/'+this.month+'/'+this.year+' '+String.format("%02d",this.hour)+':'+String.format("%02d",this.minute);
        System.out.println(output);
    }

}
