package com.example.rohan.rohan_countbook;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CounterListFragment.OnCounterCardSelectedListener {

    public List<Counter> mCounters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testData();

        initializeCounterList();

        Toolbar t = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(t);

    }

    private void initializeCounterList() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, new CounterListFragment());
        ft.addToBackStack(null);
        ft.commit();
    }


    private void testData() {
        mCounters =  new ArrayList<Counter>();
        Counter c1 = new Counter("Buy Eggs", "List of eggs to buy", 10);
        mCounters.add(c1);
    }
    @Override
    //IMPLEMENT SHOWING DETAILS VIEW, to edit exiting items
    public void onCounterSelected(int index) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, CounterDetailsFragment.newInstance(index));
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addCounter:
                // Add a new counter
                return true;


            default:
                // default behaviour calls superclass
                return super.onOptionsItemSelected(item);

        }

    }
}
