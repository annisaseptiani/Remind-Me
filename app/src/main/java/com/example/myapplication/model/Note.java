package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note  {
   /* public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "idx";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TIMESTAMP = "timestamp";
*/
    private String idx;
    private String title;
    private String des;
    private String timestamp;
    private String date;

    public Note() {
    }

    public Note(String idx, String title, String priority, String des, String timestamp, String date) {
        this.idx = idx;
        this.title = title;
        this.des = des;
        this.timestamp = timestamp;
        this.date = date;
    }


    protected Note(Parcel in) {
        idx = in.readString();
        title = in.readString();
        des = in.readString();
        timestamp = in.readString();
        date = in.readString();
    }

    public String  getId() {
        return idx;
    }

    public void setId(String idx) {
        this.idx = idx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }


    public String getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
