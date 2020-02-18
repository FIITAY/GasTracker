package itay.finci.org.gastracker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import itay.finci.org.gastracker.Bill.BillList;
import itay.finci.org.gastracker.fragments.AddBill;
import itay.finci.org.gastracker.fragments.Summery;
import itay.finci.org.gastracker.fragments.listOfBills;

public class MainActivity extends AppCompatActivity {

    public static final String FILE_NAME = "billListData.bld";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //see summery
                    Summery s = new Summery();
                    replaceFragment(s);
                    return true;
                case R.id.navigation_dashboard:
                    //see all enteries
                    listOfBills lob = new listOfBills();
                    replaceFragment(lob);
                    return true;
                case R.id.navigation_notifications:
                    //add new entery
                    AddBill ab = new AddBill();
                    replaceFragment(ab);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //read the file to get previous saved data
        readData(getApplicationContext());
        //initiate in the summery screen
        Summery s = new Summery();
        replaceFragment(s);

        //finds the BottomNavigationView a and add the navigation listiner
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void readData(Context context) {
        try {
            InputStream fis = context.openFileInput(MainActivity.FILE_NAME);
            if(fis != null){
                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString + "\n");
                }

                fis.close();
                String ret = stringBuilder.toString();

                //pass the file content to the BillList to parse it into bills
                BillList.getInstance().parseString(ret);
            }
        }catch(Exception e){
            //do not worry no file
            return;
        }
    }

    /**
     * replace fragment function
     *
     * @param fragment fragment object to change to
     */
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, null);
        ft.detach(fragment);
        ft.attach(fragment);
        ft.commit();
    }
}
