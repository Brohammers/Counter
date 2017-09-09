package com.example.rohan.rohan_countbook;

import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rohan on 9/6/2017.
 */

public class CounterListFragment extends Fragment {

    OnSelectedListener mCallback;

    public interface OnSelectedListener {

        void onCounterSelected(int index);
        void onAddSelected();

    }

    private TextView mCounterSummary;
    private RecyclerView mRecyclerView;
    private CounterAdapter mCounterAdapter;
    private CounterStorage mCounterStorage;

    public static CounterListFragment newInstance() {
        return new CounterListFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mCallback = (OnSelectedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnSelectedListener interface!");
        }
        mCounterStorage = CounterStorage.getCounterStorage();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.counter_list, container, false);
        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCounterAdapter = new CounterAdapter(mCounterStorage);
        mRecyclerView.setAdapter(mCounterAdapter);

        mCounterSummary = (TextView) v.findViewById(R.id.numberOfCounters);
        mCounterSummary.setText("Total number of counters: " + Integer.toString(mCounterStorage.getCounters().size()));

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu);

        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addCounter:
                mCallback.onAddSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class CounterAdapter extends RecyclerView.Adapter<CounterAdapter.CounterHolder> {

        private CounterStorage mCounterStorage;

        public CounterAdapter(CounterStorage counterStorage) {
            this.mCounterStorage = counterStorage;
        }

        @Override
        public CounterHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.counter_card, null);
            CounterHolder counterHolder = new CounterHolder(v);
            return counterHolder;
        }

        @Override
        public void onBindViewHolder(CounterHolder counterHolder, final int index) {
            counterHolder.onBind(mCounterStorage.getCounters().get(index), index);
        }

        private void updateUI (int index) {
            mCounterAdapter.notifyItemChanged(index);
            mCounterSummary.setText("Total number of counters: " + Integer.toString(mCounterStorage.getCounters().size()));
        }

        @Override
        public int getItemCount() {
            return mCounterStorage.getCounters().size();
        }


        public class CounterHolder extends RecyclerView.ViewHolder {

            public TextView mName;
            public TextView mCurrentValue;
            public TextView mDate;
            public ImageButton mIncrement;
            public ImageButton mDecrement;

            public CounterHolder(View itemView) {

                super(itemView);

                this.mName = (TextView) itemView.findViewById(R.id.name);
                this.mCurrentValue = (TextView) itemView.findViewById(R.id.currentValue);
                this.mDate = (TextView) itemView.findViewById(R.id.currentDateStamp);
                this.mIncrement = (ImageButton) itemView.findViewById(R.id.incrementCounter);
                this.mDecrement = (ImageButton) itemView.findViewById(R.id.decrementCounter);
            }

            public void onBind(final Counter counter, final int index) {
                mName.setText(counter.getName());
                mCurrentValue.setText(Integer.toString(counter.getCurrentValue()));
                mDate.setText(counter.getLastModifiedDate());
                mIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        counter.incrementValue();
                        mCounterStorage.saveCounters(getActivity());
                        updateUI(index);
                    }
                });
                mDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        counter.decrementValue();
                        mCounterStorage.saveCounters(getActivity());
                        updateUI(index);
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCallback.onCounterSelected(index);
                    }
                });
            }
        }
    }

}
