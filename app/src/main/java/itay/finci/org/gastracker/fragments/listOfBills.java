package itay.finci.org.gastracker.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itay.finci.org.gastracker.Bill.Bill;
import itay.finci.org.gastracker.Bill.BillList;
import itay.finci.org.gastracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class listOfBills extends Fragment {


    public listOfBills() {
        // Required empty public constructor
    }

    RecyclerView listView;
    LinearLayoutManager layoutManager;;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View root = inflater.inflate(R.layout.fragment_list_of_bills, container, false);

         //finds the list
        listView = (RecyclerView) root.findViewById(R.id.rv);

        //add the layout manager to the list
        layoutManager = new LinearLayoutManager(getContext());
        listView.setLayoutManager(layoutManager);

        //add the adapter to the list
        final StableArrayAdapter adapter = new StableArrayAdapter(BillList.getInstance().getList(), getContext());
        listView.setAdapter(adapter);



        return root;

    }

}

class StableArrayAdapter extends RecyclerView.Adapter<StableArrayAdapter.MyViewHolder> {

    /***
     * a new type of view that holds each row
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvPrice, tvDate, tvKm,tvLiters;

        /**
         * takes the row view and populate each valuble with his TextView
         * @param v the view that is the parent of this object
         */
        public MyViewHolder(View v) {
            super(v);
            this.tvPrice = v.findViewById(R.id.tvPrice);
            this.tvDate = v.findViewById(R.id.tvDate);
            this.tvKm = v.findViewById(R.id.tvKm);
            this.tvLiters = v.findViewById(R.id.tvLiters);
        }
    }

    private ArrayList<Bill> arrayList;
    private LayoutInflater mInflater;

    /**
     * constructor
     * @param objects the ArrayList of the objects
     * @param context the application context
     */
    public StableArrayAdapter(ArrayList<Bill> objects, Context context){
        arrayList = objects;
        this.mInflater = LayoutInflater.from(context);

    }

    /**
     * makes new view for each row
     * @return the new row
     */
    @Override
    public StableArrayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View view = mInflater.inflate(R.layout.sample_row_layout, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * changes the text of the TextView in the row
     * @param holder the view of the row
     * @param position the row number
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Bill b = arrayList.get(position);
        holder.tvPrice.setText(" " + Double.toString(b.getPrice()));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        holder.tvDate.setText(" " + df.format(b.getDate()));
        holder.tvKm.setText(" "+ Double.toString(b.getKilometers()));
        holder.tvLiters.setText(" "+ Double.toString(b.getLiters()));
    }

    /**
     * required function
     * @return the number of rows in the list
     */
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
