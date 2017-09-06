package com.example.rohan.rohan_countbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Rohan on 9/6/2017.
 */

public class CounterListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.counter_list, container, false);

        //do all your view setup here


        return v;
    }



    public class CounterAdapter extends RecyclerView.Adapter<CounterAdapter.CounterHolder> {

        private List<Counter> mCounterList;

        public CounterAdapter(List<Counter> counterList) {
            this.mCounterList = counterList;
        }

        @Override
        public CounterHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.counter_list, null));
            CounterHolder counterHolder = new CounterHolder(v);
            return counterHolder;
        }


        @Override
        public void onBindViewHolder(CounterHolder counterHolder, int index) {
            Counter counter = mCounterList.get(index);



            counterHolder.mName.setText(counter.getName());
            counterHolder.mCurrentValue.setText(counter.getCurrentValue());
            counterHolder.mDate.setText(counter.getLastModifiedDate());
            counterHolder.mDescription.setText(counter.getComment());

            counterHolder.mIncrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Increment counter

                }
            });





        }

        @Override
        public int getItemCount() {
            return mCounterList.size();
        }


        public class CounterHolder extends RecyclerView.ViewHolder {

            public TextView mName;
            public TextView mCurrentValue;
            public TextView mDate;
            public TextView mDescription;

            public ImageButton mIncrement;
            public ImageButton mDecrement;

            public CounterHolder(View view) {

                super(view);

                this.mName = (TextView) view.findViewById(R.id.name);
                this.mCurrentValue = (TextView) view.findViewById(R.id.currentValue);
                this.mDate = (TextView) view.findViewById(R.id.currentDateStamp);
                this.mDescription = (TextView) view.findViewById(R.id.Description);

                this.mIncrement = (ImageButton) view.findViewById(R.id.incrementCounter);
                this.mDecrement = (ImageButton) view.findViewById(R.id.decrementCounter);
            }
        }
    }

}
