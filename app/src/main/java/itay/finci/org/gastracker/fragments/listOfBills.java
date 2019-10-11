package itay.finci.org.gastracker.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View root = inflater.inflate(R.layout.fragment_list_of_bills, container, false);

        listView = (ListView) root.findViewById(R.id.lv);

        final StableArrayAdapter adapter = new StableArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1, BillList.getInstance().getList());
        listView.setAdapter(adapter);

        return root;

    }

}

class StableArrayAdapter extends ArrayAdapter<Bill> {
    private final Context context;
    HashMap<Bill, Integer> mIdMap = new HashMap<Bill, Integer>();

    public StableArrayAdapter(Context context, int textViewResourceId,
                              ArrayList<Bill> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        Bill item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.sample_row_layout, parent, false);
        TextView tvPrice = (TextView) rowView.findViewById(R.id.tvPrice);
        TextView tvDate = (TextView) rowView.findViewById(R.id.tvDate);
        TextView tvKm = (TextView) rowView.findViewById(R.id.tvKm);
        TextView tvLiters = (TextView) rowView.findViewById(R.id.tvLiters);

        Bill b = getItem(position);

        tvPrice.setText(" " + Double.toString(b.getPrice()));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        tvDate.setText(" " + df.format(b.getDate()));
        tvKm.setText(" "+ Double.toString(b.getKilometers()));
        tvLiters.setText(" "+ Double.toString(b.getLiters()));

        return rowView;
    }

}
