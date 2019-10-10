package itay.finci.org.gastracker.Bill;


import java.util.Date;

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


}
