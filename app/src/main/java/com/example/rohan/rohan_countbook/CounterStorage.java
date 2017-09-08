package com.example.rohan.rohan_countbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohan on 9/7/2017.
 */

public class CounterStorage {

    private List<Counter> mCounters;
    private Context mContext;
    private static final String SAVED_DATA_KEY = "com.example.rohan.rohan_book.CounterStorage";

    private static CounterStorage mCounterStorage = null;

    public static CounterStorage getCounterStorage(Context context) {

        if (mCounterStorage == null) {
            mCounterStorage = new CounterStorage(context);
            return mCounterStorage;
        }
        return mCounterStorage;

    }

    private CounterStorage(Context context) {
        mContext = context.getApplicationContext();
        retrieveCounters();
    }

    public List<Counter> getCounters() {
        return mCounters;
    }

    public void retrieveCounters() {
        SharedPreferences counterData = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        Gson gson = new Gson();
        String JSONArrayCounters = counterData.getString(SAVED_DATA_KEY, "");


        if (!JSONArrayCounters.equals("")) {
            mCounters = gson.fromJson(JSONArrayCounters,new TypeToken<List<Counter>>(){}.getType());
        } else {
            mCounters = new ArrayList<Counter>();
        }
    }

    public void saveCounters() {

        SharedPreferences counterData = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        Gson gson = new Gson();
        String jsonCounters = gson.toJson(mCounters);

        SharedPreferences.Editor prefsEditor = counterData.edit();
        prefsEditor.putString(SAVED_DATA_KEY, jsonCounters);
        prefsEditor.commit();

        retrieveCounters();

    }


}
