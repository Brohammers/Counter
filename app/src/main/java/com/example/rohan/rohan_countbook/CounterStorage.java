package com.example.rohan.rohan_countbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohan on 9/7/2017.
 * Purpose: This is the CounterStorage class and is the central storage point for counters.
 *          This fragment serves three distinct purposes:
 *          1. Save existing counter data to disk
 *          2. Retrieve counter data (if present) from disk.
 *          3. Provide a list of all counters to the rest of the application.
 *
 *  Design Rationale: This class was created to have a centralized load/store (storage) mechanism.
 *                      If the storage details are changed, only this class should be affected. Prevents
 *                      code duplication as both CounterDetailsFragment and CounterListFragment
 *                      retrieve, modify and save the counter data.
 *  Notes. 1. This class uses the Java Singleton pattern and only instantiates ONE instance of its type.
 *              As such, only the main activity instantiates the instance (and holds a reference to it for GC purposes).
 *
 */

public class CounterStorage {

    private List<Counter> mCounters;
    private static final String SAVED_DATA_KEY = "com.example.rohan.rohan_book.CounterStorage";
    private static CounterStorage mCounterStorage = null;

    public static CounterStorage getCounterStorage() {

        if (mCounterStorage == null) {
            mCounterStorage = new CounterStorage();
            return mCounterStorage;
        }
        return mCounterStorage;

    }

    private CounterStorage() {
       initCounters();
    }

    public List<Counter> getCounters() {
        return mCounters;
    }

    public void initCounters() {
        mCounters = new ArrayList<Counter>();
    }

    public void retrieveCounters(Context context) {
        SharedPreferences counterData = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String JSONArrayCounters = counterData.getString(SAVED_DATA_KEY, "");

        if (!JSONArrayCounters.equals("")) {
            mCounters = gson.fromJson(JSONArrayCounters,new TypeToken<List<Counter>>(){}.getType());
        }
    }

    public void saveCounters(Context context) {
        SharedPreferences counterData = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String jsonCounters = gson.toJson(mCounters);

        SharedPreferences.Editor prefsEditor = counterData.edit();
        prefsEditor.putString(SAVED_DATA_KEY, jsonCounters);
        prefsEditor.commit();

        retrieveCounters(context);
    }


}
