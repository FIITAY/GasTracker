package itay.finci.org.gastracker.fragments;


import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import itay.finci.org.gastracker.Bill.Bill;
import itay.finci.org.gastracker.Bill.BillList;
import itay.finci.org.gastracker.MainActivity;
import itay.finci.org.gastracker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddBill extends Fragment {


    public AddBill() {
        // Required empty public constructor
        position = -1;
    }

    public AddBill(int position){
        this.position = position;
    }

    Button btSubmit;
    EditText etPrice, etLiters, etKm, etDate;
    int position;

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

        //if have position load the data
        if(position != -1){
            Bill b = BillList.getInstance().get(position);
            etPrice.setText(""+b.getPrice());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            etDate.setText(""+df.format(b.getDate()));
            etKm.setText(""+b.getKilometers());
            etLiters.setText(""+b.getLiters());
        }

        //get click listiner for btSubmit
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //gets the data from the edit text fields
                    double price  = Double.parseDouble(etPrice.getText().toString());
                    double liters = Double.parseDouble(etLiters.getText().toString());
                    double km     = Double.parseDouble(etKm.getText().toString());
                    Date bought;
                    if(!etDate.getText().toString().isEmpty()) {
                        try { //try to parse the date given in the edit text
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            bought = df.parse(etDate.getText().toString());
                        } catch (Exception e) {
                            bought = new Date(); //error so the date is today
                        }
                    }else{
                        bought = new Date(); // the date is today because empty field
                    }
                    //make a new bill with the data given
                    Bill bill = new Bill(price, liters, km, bought);
                    if(position == -1){
                        //add the new bill
                        BillList.getInstance().add(bill);
                    }else {
                        //edit mode
                        BillList.getInstance().set(bill,position);//make the position equal to the new bill
                    }
                    //re write the bill list in to a file
                    wirteToFile(getContext());
                    //get back to the summery screen
                    Summery s = new Summery();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, s, null);
                    ft.detach(s);
                    ft.attach(s);
                    ft.commit();
                }catch(Exception e){//error in getting the new bill, show error message
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Error while proccessing the data", Toast.LENGTH_LONG).show();
                }
            }
        });

        //returns view
        return root;
    }

    private void wirteToFile(Context context){
        context.deleteFile(MainActivity.FILE_NAME);
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(MainActivity.FILE_NAME, Context.MODE_PRIVATE);
            String out = BillList.getInstance().getInstance().toString();
            fos.write(out.getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        //make beckup file
        File backup = new File(context.getExternalFilesDir(null) +"/" +MainActivity.FILE_NAME);
        Log.e("FILE:",""+context.getExternalFilesDir(null) +"/" +MainActivity.FILE_NAME);
        backup.delete();
        try {
            fos = new FileOutputStream(context.getExternalFilesDir(null) +"/" +MainActivity.FILE_NAME);
            String out = BillList.getInstance().getInstance().toString();
            fos.write(out.getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
