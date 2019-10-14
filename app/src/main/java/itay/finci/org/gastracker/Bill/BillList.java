package itay.finci.org.gastracker.Bill;

import java.util.ArrayList;

public class BillList {
    private static final BillList ourInstance = new BillList();

    public static BillList getInstance() {
        return ourInstance;
    }

    private BillList() {
        list = new ArrayList<Bill>();
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
}
