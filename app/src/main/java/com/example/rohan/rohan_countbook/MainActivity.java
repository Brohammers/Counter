package com.example.rohan.rohan_countbook;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CounterListFragment.OnCounterCardSelectedListener,
        CounterListFragment.OnAddSelectedListener, CounterDetailsFragment.OnCounterModifiedListener {

    public List<Counter> mCounters = new ArrayList<Counter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //testData();
        loadExistingData();

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
        Counter c2 = new Counter("Buy Eggs", "List of eggs to buy", 11);
        Counter c3 = new Counter("Buy Eggs", "List of eggs to buy", 12);
        Counter c4 = new Counter("Buy Eggs", "List of eggs to buy", 13);
        Counter c5 = new Counter("Buy Eggs", "List of eggs to buy", 14);
        Counter c6 = new Counter("Buy Eggs", "List of eggs to buy", 15);
        Counter c7 = new Counter("Buy Eggs", "List of eggs to buy", 17);
        Counter c8 = new Counter("Buy Eggs", "List of eggs to buy", 112);
        Counter c9 = new Counter("Buy Eggs", "List of eggs to buy", 11);
        Counter c10 = new Counter("Buy Eggs", "List of eggs to buy", 10);
        Counter c11 = new Counter("Buy Eggs", "List of eggs to buy", 9);

        mCounters.add(c1);
        mCounters.add(c2);
        mCounters.add(c3);
        mCounters.add(c4);
        mCounters.add(c5);
        mCounters.add(c6);
        mCounters.add(c7);
        mCounters.add(c8);
        mCounters.add(c9);
        mCounters.add(c10);
        mCounters.add(c11);

    }

    private void loadExistingData() {
        SharedPreferences counterData = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String JSONArrayCounters = counterData.getString("Saved Data", "");


        if (!JSONArrayCounters.equals("")) {
            mCounters = gson.fromJson(JSONArrayCounters,new TypeToken<List<Counter>>(){}.getType());
        } else {
            mCounters = new ArrayList<Counter>();
        }
    }


    private void saveData() {
        SharedPreferences counterData = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String jsonCounters = gson.toJson(mCounters);

        SharedPreferences.Editor prefsEditor = counterData.edit();
        prefsEditor.putString("Saved Data", jsonCounters);
        prefsEditor.commit();
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
    public void onIncrementDecrementSelected() {
        saveData();
    }

    @Override
    public void onCounterModified() {
        saveData();
        initializeCounterList();
    }

    @Override
    public void onAddSelected() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, new CounterDetailsFragment());
        ft.addToBackStack(null);
        ft.commit();
    }
}
