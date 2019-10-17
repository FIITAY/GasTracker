package itay.finci.org.gastracker.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import itay.finci.org.gastracker.Bill.Bill;
import itay.finci.org.gastracker.Bill.BillList;
import itay.finci.org.gastracker.R;

/**
 * represent month and year
 */
class Month{
    public String month;
    public String year;

    public Month(String month, String year){
        this.month = month;
        this.year  = year;
    }
}

/**
 * A simple {@link Fragment} subclass.
 */
public class Summery extends Fragment {




    public Summery() {
        // Required empty public constructor
    }

    LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_summery, container, false);

        //finds the layout
        RecyclerView relativeLayout = (RecyclerView) root.findViewById(R.id.rvList);

        //attach a layout manager
        layoutManager = new LinearLayoutManager(getContext());
        relativeLayout.setLayoutManager(layoutManager);

        //calculate the months
        Month[] objects = new Month[12];
        DateFormat findMouth = new SimpleDateFormat("MM");
        DateFormat findYear = new SimpleDateFormat("yyyy");
        double mToday = Double.parseDouble(findMouth.format(new Date()));
        double yToday = Double.parseDouble(findYear.format(new Date()));
        for(int i = 0;i<objects.length;i++){
            if(mToday - i > 9){
               objects[i] = new Month("" +(int)(mToday - i),""+(int)yToday);
            }else if(mToday - i > 0){
                objects[i] = new Month("0" +(int)(mToday - i),""+(int)yToday);
            }else{//remove year
                if((int)(12 + (mToday - i)) > 9){
                    objects[i] = new Month(""+(int)(12 + (mToday - i)), ""+(int)(yToday-1));
                }else{
                    objects[i] = new Month("0"+(int)(12 + (mToday - i)), ""+(int)(yToday-1));
                }

            }
        }
        //attach adapter
        relativeLayout.setAdapter(new SimpleAdapter(objects, getContext()));

        //return the view
        return root;
    }
}



class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder>{

    /**
     * new type of view that holdes each row
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //each data item
        public TextView tvPaid, tvKm, tvLiters, tvMonth, tvKmLiter;
        public LinearLayout llColor;
        public ViewHolder(View v){
            super(v);
            this.tvPaid    = v.findViewById(R.id.tvPaiad);
            this.tvKm      = v.findViewById(R.id.tvKm);
            this.tvLiters  = v.findViewById(R.id.tvLiters);
            this.tvMonth   = v.findViewById(R.id.tvMonth);
            this.tvKmLiter = v.findViewById(R.id.tvKmLiter);
            this.llColor   = v.findViewById(R.id.llColor);
        }
    }

    private Month[] array;
    private LayoutInflater mInflater;
    private Context mContext;

    public SimpleAdapter(Month[] objects, Context context){
        this.array = objects;
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    /**
     * makes new view for each row
     * @return the new row
     */
    @Override
    public SimpleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.sample_row_summery, parent, false);
        return new ViewHolder(view);
    }

    /**
     * changes the text of the TextView in the row
     * @param holder the view of the row
     * @param position the row number
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Month month = array[position];
        double paid =0, km = 0, liters = 0;
        double sumKm =0, sumLiters = 0; //to calculate the total km/liter
        ArrayList<Bill> arrayList = BillList.getInstance().getList();
        DateFormat findMouth = new SimpleDateFormat("MM");
        DateFormat findYear = new SimpleDateFormat("yyyy");
        for(Bill b: arrayList){//go over all of the array list
            if(findMouth.format(b.getDate()).equals(month.month) &&
                    findYear.format(b.getDate()).equals(month.year)){
                paid += b.getPrice();
                km += b.getKilometers();
                liters += b.getLiters();
            }
            sumKm +=  b.getKilometers();
            sumLiters += b.getLiters();
        }

        holder.tvLiters.setText(" "+liters);
        holder.tvPaid.setText(" "+paid);
        holder.tvKm.setText(" "+km);
        holder.tvKmLiter.setText(" "+ ((int)((km/liters) * 100) / 100.0));
        String m = "error";
        switch (month.month){
            case "01":
                m = "January";
                break;
            case "02":
                m = "February";
                break;
            case "03":
                m = "March";
                break;
            case "04":
                m = "April";
                break;
            case "05":
                m = "May";
                break;
            case "06":
                m = "June";
                break;
            case "07":
                m = "July";
                break;
            case "08":
                m = "August";
                break;
            case "09":
                m = "September";
                break;
            case "10":
                m = "October";
                break;
            case "11":
                m = "November";
                break;
            case "12":
                m = "December";
                break;
        }
        holder.tvMonth.setText(m);

        //change the color of the background if th km/liters is less then avg
        if(sumKm/sumLiters > km/liters){
            holder.llColor.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red));
        }
    }

    /**
     * required function
     * @return the number of rows in the list
     */
    @Override
    public int getItemCount() {
        return array.length;
    }

}
