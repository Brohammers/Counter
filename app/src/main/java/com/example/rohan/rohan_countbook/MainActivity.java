package com.example.rohan.rohan_countbook;


/**
 * Created by Rohan on 9/6/2017.
 * Purpose: This is the main activity and is the entry point of the app.
 *          This activity serves three distinct purposes:
 *          1. Initialize the counter storage class, which is used to store and retrieve counter data
 *          2. Call upon [2] children fragments - CounterListFragment and CounterDetailsFragment
 *              a. CounterListFragment is used to display the list of all counters
                b. CounterDetailsFragment is used to display the details of a single counter
 *              c. By implementing listeners from CounterListFragment and CounterDetailsFragment,
 *                  this activity passes information (counter index number) from the list fragment
 *                  to the details fragment.
 *          3. Initialize the toolbar for supporting add and delete functions.
 *              a. The Add function is implemented in the list fragment
 *              b. The delete function is implemented in the details fragment
 *
 *  Design Rationale: By separating the list view and details view into two separate fragments, with
 *                      this activity acting as the intermediary between the two fragment classes,
 *                      the code is flexible - Each of the views can be independently customized and maintained.
 *
 */

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

    private CounterStorage mCounterStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCounterStorage = CounterStorage.getCounterStorage();
        mCounterStorage.retrieveCounters(this);

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
    public void onCounterSavedOrDeleted() {
        initializeCounterList();
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        }
        super.onBackPressed();
    }
}
