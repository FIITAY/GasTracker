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

    public ArrayList<Bill> getList() {
        return list;
    }
}
