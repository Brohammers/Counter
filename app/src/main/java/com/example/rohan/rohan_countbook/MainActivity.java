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

public class MainActivity extends AppCompatActivity implements CounterListFragment.OnSelectedListener,
        CounterDetailsFragment.OnCounterModifiedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CounterStorage.getCounterStorage().retrieveCounters(this);

        setToolbar();
        initializeCounterList();
    }

    private void setToolbar() {
        Toolbar t = (Toolbar) findViewById(R.id.my_toolbar);
        t.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(t);
    }

    private void initializeCounterList() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, CounterListFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCounterSelected(int index) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, CounterDetailsFragment.newInstance(index))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddSelected() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, new CounterDetailsFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCounterModified() {
        initializeCounterList();
    }
}
