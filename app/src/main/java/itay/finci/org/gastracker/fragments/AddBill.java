package itay.finci.org.gastracker.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import itay.finci.org.gastracker.Bill.Bill;
import itay.finci.org.gastracker.Bill.BillList;
import itay.finci.org.gastracker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddBill extends Fragment {


    public AddBill() {
        // Required empty public constructor
    }

    Button btSubmit;
    EditText etPrice, etLiters, etKm, etDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_add_bill, container, false);

        //initiate all buttons and edit texts
        btSubmit = (Button)   root.findViewById(R.id.btSubmit);
        etPrice  = (EditText) root.findViewById(R.id.etPrice);
        etLiters = (EditText) root.findViewById(R.id.etLiters);
        etKm     = (EditText) root.findViewById(R.id.etKm);
        etDate   = (EditText) root.findViewById(R.id.etDate);

        //get click listiner for btSubmit
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double price  = Double.parseDouble(etPrice.getText().toString());
                    double liters = Double.parseDouble(etLiters.getText().toString());
                    double km     = Double.parseDouble(etKm.getText().toString());
                    Date bought;
                    if(!etDate.getText().toString().isEmpty()) {
                        try {
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            bought = df.parse(etDate.getText().toString());
                        } catch (Exception e) {
                            bought = new Date(); //error so the date is today
                        }
                    }else{
                        bought = new Date(); // the date is today because empty field
                    }
                    Bill bill = new Bill(price, liters, km, bought);
                    BillList.getInstance().add(bill);
                    //get back to the summery screen
                    Summery s = new Summery();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, s, null);
                    ft.detach(s);
                    ft.attach(s);
                    ft.commit();
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Error while proccessing the data", Toast.LENGTH_LONG).show();
                }
            }
        });

        //returns view
        return root;
    }

}
