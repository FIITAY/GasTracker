package itay.finci.org.gastracker.Bill;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class BillList {

    private static final BillList ourInstance = new BillList();

    public static BillList getInstance() {
        return ourInstance;
    }

    private BillList(){
        list = new ArrayList<Bill>();
//        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            add(new Bill(1, 1, 1, df.parse("17/10/2019")));
//            add(new Bill(2, 2, 2, df.parse("17/09/2019")));
//            add(new Bill(3, 3, 3, df.parse("17/08/2019")));
//            add(new Bill(4, 4, 4, df.parse("17/07/2019")));
//            add(new Bill(5, 5, 5, df.parse("17/06/2019")));
//            add(new Bill(6, 6, 6, df.parse("17/05/2019")));
//            add(new Bill(7, 7, 7, df.parse("17/04/2019")));
//            add(new Bill(8, 8, 8, df.parse("17/03/2019")));
//            add(new Bill(9, 9, 9, df.parse("17/02/2019")));
//            add(new Bill(10, 10, 10, df.parse("17/01/2019")));
//            add(new Bill(11, 11, 11, df.parse("17/12/2018")));
//            add(new Bill(12, 12, 12, df.parse("17/11/2018")));
//            add(new Bill(13, 13, 13, df.parse("17/10/2018")));
//            add(new Bill(14, 14, 14, df.parse("17/09/2018")));
//            add(new Bill(15, 15, 15, df.parse("17/08/2018")));
//        }catch (Exception  e){
//            e.printStackTrace();
//        }
    }

    private ArrayList<Bill> list;

    public void add(Bill b){
        list.add(b);
    }

    public Bill get(int position){
        return list.get(position);
    }

    public void set(Bill b, int position){
        list.remove(position); //removes the previous bill
        list.add(position, b); //adds the new bill in the same position
    }

    public ArrayList<Bill> getList() {
        return list;
    }

    @Override
    public String toString(){
        String out="";

        //go over all of the bills and add their string into out seperated by \n
        for(Bill b : list){
            out += b.toString() + "\n";
        }

        return out;
    }

    public void parseString(String ret){
        Scanner s = new Scanner(ret); //to be able to ask for 1 value at a time
        String row = "";
        //go over all of the file line by line
        while(s.hasNext()){
            row= s.nextLine();
            if(row.isEmpty()){
                continue;
            }else{
                Bill newB= new Bill(row);
                if(!dateExsist(newB))
                    add(newB);//make new bill from this data
            }
        }
    }

    private boolean dateExsist(Bill b){
        for (Bill existing: list) {
            if(existing.getDate().equals(b.getDate()))
                return true;
        }
        return false;
    }
}
