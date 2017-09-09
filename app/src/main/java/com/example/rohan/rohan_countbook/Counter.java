package com.example.rohan.rohan_countbook;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rohan on 9/6/2017.
 */

public class Counter {

    private int mID; //to implement this ID, so that a unique one is generated each time
    private String mName;
    private String mComment;
    private String  mLastModifiedDate;
    private int mInitialValue;
    private int mCurrentValue;

    public Counter(String name, String comment, int initialValue) {
        this.mName = name;
        this.mComment = comment;
        this.mLastModifiedDate = getCurrentDate();
        this.mInitialValue = initialValue;
        this.mCurrentValue = initialValue;
        this.mID = 1;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String mComment) {
        this.mComment = mComment;
    }

    public String getLastModifiedDate() {
        return mLastModifiedDate;
    }

    public void setLastModifiedDate() {
        this.mLastModifiedDate = getCurrentDate();
    }

    public int getInitialValue() {
        return mInitialValue;
    }

    //this method is private because we do not allow this value to be modified.
    private void setInitialValue(int mInitialValue) {
        this.mInitialValue = mInitialValue;
    }

    public int getCurrentValue() {
        return mCurrentValue;
    }

    public void setCurrentValue(int mCurrentValue) {
        this.mCurrentValue = mCurrentValue;
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public void incrementValue() {
        this.setCurrentValue(this.getCurrentValue() + 1);
        this.setLastModifiedDate();
    }

    public void decrementValue() {
        if (this.getCurrentValue() == 0) {
            return;
        }
        this.setCurrentValue(this.getCurrentValue() - 1);
        this.setName(this.getName() + Integer.toString(getCurrentValue()*2));
        this.setLastModifiedDate();
    }
}
