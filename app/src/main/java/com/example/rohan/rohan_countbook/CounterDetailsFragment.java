package com.example.rohan.rohan_countbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohan on 9/6/2017.
 */

public class CounterDetailsFragment extends Fragment {

    private static final String IS_VALID_KEY = "com.example.rohan.rohan_countBook.CounterDetailsFragment.IS_VALID_KEY";
    private static final String INDEX_KEY = "com.example.rohan.rohan_countBook.CounterDetailsFragment.INDEX_KEY";


    private int mIndex;
    private boolean mIsNew;

    private EditText mName;
    private EditText mInitialValue;
    private EditText mCurrentValue;
    private EditText mDescription;
    private TextView mDate;

    private Button mResetCounter;
    private Button mSave;

    private CounterStorage mCounterStorage;
    private Counter mCounter;

    OnCounterModifiedListener mCallback;

    public interface OnCounterModifiedListener {
        void onCounterModified();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnCounterModifiedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement interface");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_details, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.deleteCounter:
                if (!mIsNew) {
                    mCounterStorage.getCounters().remove(mIndex);
                    mCounterStorage.saveCounters(getActivity());
                    mCallback.onCounterModified();
                    Toast.makeText(getActivity(), "Counter deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "You are currently in the create new counter form!", Toast.LENGTH_SHORT).show();
                }

            default:
                // default behaviour calls superclass
                return super.onOptionsItemSelected(item);

        }

    }

    public static CounterDetailsFragment newInstance(int index) {
        Bundle args = new Bundle();
        args.putInt(INDEX_KEY, index);
        args.putBoolean(IS_VALID_KEY, true);
        CounterDetailsFragment fragment = new CounterDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.counter_details, container, false);
        setHasOptionsMenu(true);

        mName = (EditText) v.findViewById(R.id.editTextName);
        mInitialValue = (EditText) v.findViewById(R.id.editTextInitialValue);
        mCurrentValue = (EditText) v.findViewById(R.id.editTextCurrentValue);
        mDescription = (EditText) v.findViewById(R.id.editTextDescription);
        mDate = (TextView) v.findViewById(R.id.edit_date);

        mResetCounter = (Button) v.findViewById(R.id.resetCounter);
        mSave = (Button) v.findViewById(R.id.Save);

        mCounterStorage = CounterStorage.getCounterStorage();
        if (getArguments() != null && getArguments().getBoolean(IS_VALID_KEY)) {
            mIndex = getArguments().getInt(INDEX_KEY);
            initExistingCounter(v);
        } else {
            initNewCounter(v);
        }

        return v;
    }

    private void initExistingCounter(View v) {
        mIsNew = false;
        mCounter = mCounterStorage.getCounters().get(mIndex);

        mName.setText(mCounter.getName());
        mInitialValue.setText(Integer.toString(mCounter.getInitialValue()));

        mCurrentValue.setText(Integer.toString(mCounter.getCurrentValue()));
        mCurrentValue.setEnabled(true);

        mDescription.setText(mCounter.getComment());
        mDate.setText(mCounter.getLastModifiedDate());

        mResetCounter.setEnabled(true);
        mResetCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentValue.setText(Integer.toString(mCounter.getInitialValue()));

            }
        });

        mSave.setEnabled(true);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (! (mCurrentValue.getText().toString().equals( mCounterStorage.getCounters().get(mIndex).getCurrentValue())) ) {
                   mCounterStorage.getCounters().get(mIndex).setLastModifiedDate();

                }
                mCounterStorage.getCounters().get(mIndex).setName(mName.getText().toString());
                mCounterStorage.getCounters().get(mIndex).setCurrentValue(Integer.valueOf(mCurrentValue.getText().toString()));
                mCounterStorage.getCounters().get(mIndex).setComment(mDescription.getText().toString());
                mCounterStorage.saveCounters(getActivity());

                Toast.makeText(getActivity(), "Counter successfully saved!", Toast.LENGTH_SHORT).show();
                mCallback.onCounterModified();
            }
        });
    }

    private void initNewCounter(View v) {
        mIsNew = true;

        mInitialValue.setEnabled(true);


        mSave.setEnabled(true);
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (  (mName.getText().toString().equals("")) && (mInitialValue.getText().toString().equals("")) ) {

                    Toast.makeText(getActivity(), "Please enter name and initial value for counter", Toast.LENGTH_SHORT).show();
                    return;

                } else if (mName.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "Please enter name for counter", Toast.LENGTH_SHORT).show();
                    return;

                } else if (mInitialValue.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "Please enter initial value for counter", Toast.LENGTH_SHORT).show();
                    return;

                }

                Counter newCounter = new Counter(mName.getText().toString(), mDescription.getText().toString(), Integer.valueOf(mInitialValue.getText().toString()));
                mCounterStorage.getCounters().add(newCounter);
                mCounterStorage.saveCounters(getActivity());

                Toast.makeText(getActivity(), "Counter successfully saved!", Toast.LENGTH_SHORT).show();

                mCallback.onCounterModified();

            }
        });


    }

}
