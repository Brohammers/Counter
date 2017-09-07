package com.example.rohan.rohan_countbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohan on 9/6/2017.
 */

public class CounterDetailsFragment extends Fragment {

    private int mIndex;

    private EditText mName;

    public static CounterDetailsFragment newInstance(int index) {

        Bundle args = new Bundle();
        args.putInt("index", index);
        CounterDetailsFragment fragment = new CounterDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.counter_details, container, false);

        mIndex = getArguments().getInt("index");

        mName = (EditText) v.findViewById(R.id.editTextName);
        mName.setText(((MainActivity) getActivity()).mCounters.get(mIndex).getName());


        //TODO: do the detail all your view setup here


        return v;
    }
}
