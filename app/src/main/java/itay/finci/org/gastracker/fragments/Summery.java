package itay.finci.org.gastracker.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import itay.finci.org.gastracker.Bill.Bill;
import itay.finci.org.gastracker.Bill.BillList;
import itay.finci.org.gastracker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Summery extends Fragment {


    public Summery() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_summery, container, false);

        TextView tvPaid = (TextView) root.findViewById(R.id.tvPaid);
        ArrayList<Bill> bills = BillList.getInstance().getList();
        Date today = new Date();
        int paidSum = 0;
        DateFormat findMouth = new SimpleDateFormat("MM");
        DateFormat findYear = new SimpleDateFormat("yyyy");
        for(Bill b : bills){
            if(findMouth.format(today).equals(findMouth.format(b.getDate())) && //check same month
               findYear.format(today).equals(findYear.format(b.getDate())) ){ //check same year
                //same month add:
                paidSum += b.getPrice();
            }
        }
        tvPaid.setText(" "+paidSum+" NIS");
        //return the view
        return root;
    }

}
