package itay.finci.org.gastracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import itay.finci.org.gastracker.fragments.AddBill;
import itay.finci.org.gastracker.fragments.Summery;
import itay.finci.org.gastracker.fragments.listOfBills;

public class MainActivity extends AppCompatActivity {

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


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
