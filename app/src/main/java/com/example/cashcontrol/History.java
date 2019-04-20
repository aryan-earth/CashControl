package com.example.cashcontrol;
import java.text.SimpleDateFormat;

public class History {
    private int id;
    private String money;
    private String time;

    public History(int id, String m) {
        this.id = id;
        this.money = m;
    }

    public int getId() {
        return id;
    }

    public String getMoney() {
        return money;
    }

    public String getTime() {
        DateAndTime();
        return time;
    }

    public void DateAndTime() {
        //Get current date and time
        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        time  = sdf.format(date);
    }

}