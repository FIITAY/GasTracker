package itay.finci.org.gastracker.Bill;


import android.media.MediaPlayer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Bill {
    private double price;
    private Date date;
    private double kilometers;
    private double liters;

    public Bill(double price, double liters, double kilometers){
        date = new Date();
        this.price = price;
        this.liters = liters;
        this.kilometers = kilometers;
    }

    public Bill(double price, double liters, double kilometers, Date bought){
        if(bought == null){
            date = new Date();
        }else{
            date = new Date(bought.getTime());
        }
        this.price = price;
        this.liters = liters;
        this.kilometers = kilometers;
    }

    public Bill(String row){
        Scanner s = new Scanner(row);
        this.price = s.nextDouble();
        this.kilometers = s.nextDouble();
        this.liters = s.nextDouble();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = df.parse(s.next());
        }catch (Exception e){
            date = new Date();
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    public void setDate(Date date) {
        this.date = new Date(date.getTime());
    }

    public double getKilometers() {
        return kilometers;
    }

    public void setKilometers(double kilometers) {
        this.kilometers = kilometers;
    }

    public double getLiters() {
        return liters;
    }

    public void setLiters(double liters) {
        this.liters = liters;
    }

    @Override
    public String toString(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return ""+price+" "+kilometers+" "+liters+" "+df.format(date);
    }


}
