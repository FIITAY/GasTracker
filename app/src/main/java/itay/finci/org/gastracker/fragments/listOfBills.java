package itay.finci.org.gastracker.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
        final StableArrayAdapter adapter = new StableArrayAdapter(BillList.getInstance().getList(),
                getContext(), getActivity());
        listView.setAdapter(adapter);

        //add support for swiping items left/right
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeManager(adapter));
        itemTouchHelper.attachToRecyclerView(listView);

        return root;

    }

}

/**
 * adapter that manages the representation of each item in the array list of bills into rows
 */
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
    private Activity mActivity;

    /**
     * constructor
     * @param objects the ArrayList of the objects
     * @param context the application context
     */
    public StableArrayAdapter(ArrayList<Bill> objects, Context context, Activity activity){
        arrayList = objects;
        this.mInflater = LayoutInflater.from(context);
        mActivity = activity;

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

    /**
     * lets the user remove item from the list
     * @param position
     */
    public void delete(int position){
        Bill removed = arrayList.get(position);//saves the removed object
        arrayList.remove(position);//removes the item
        notifyItemRemoved(position);//makes the list update
        showUndo(removed,position);
    }

    /**
     * show option to undo delete
     * @param removed the item that was removed
     * @param position the position to insert
     */
    private void showUndo(final Bill removed, final int position){
        View view = mActivity.findViewById(R.id.coordinatorLayout); //finds the fragment
        Snackbar snackbar = Snackbar.make(view, "Do you want to undo the action",
                Snackbar.LENGTH_LONG); //makes new snackbar
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                snackbar.getView().getLayoutParams();
        params.bottomMargin = 100; //make the snackbar above the buttom navigation view
        snackbar.getView().setLayoutParams(params);
        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    undoDelete(removed, position); //if undo pressed do the undoDeleelete action
            }
        }); //makes a action with text "UNDO" and  and attack click listener
        snackbar.show(); //showes the snackbar
    }

    /**
     * perform the actual undo
     * @param removed the item that was removed
     * @param position the position to insert
     */
    private void undoDelete(Bill removed, int position){
        arrayList.add(position, removed); //adds new item
        notifyItemInserted(position); //updates the list that new item is arrived
    }

}

/**
 * class that mangases row swiping
 */
class SwipeManager extends ItemTouchHelper.SimpleCallback{
    private StableArrayAdapter mAdapter;

    public SwipeManager(StableArrayAdapter mAdapter){
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mAdapter = mAdapter;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction){
        switch (direction){
            case ItemTouchHelper.RIGHT:
                //item swiped to the right- delete the item
                mAdapter.delete(viewHolder.getAdapterPosition());
                break;
            case ItemTouchHelper.LEFT:
                //item swiped to the left- edit the item
                break;
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // used for up and down movements
        return false;
    }

}