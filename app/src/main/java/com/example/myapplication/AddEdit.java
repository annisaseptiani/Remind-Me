package com.example.myapplication;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.database.DbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEdit extends AppCompatActivity {

    private ListView listView;
    EditText txt_id, txt_title, txt_des;
    Button btn_submit, btn_cancel,button_no_yes;
    DbHelper SQLite = new DbHelper(this);
    String id, title, des, timestamp, date;

    int hour, minute, mYear,mMonth, mDay;
    static final int TIME_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID = 1;
    private EditText txtDate;
    private EditText txtTime;
    private String[] arrMonth = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    private TextView switchStatus;
    private Switch mySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_id = (EditText) findViewById(R.id.txt_id);
        txt_title = (EditText) findViewById(R.id.txt_title);
        txt_des = (EditText) findViewById(R.id.txt_des);
        txtDate = (EditText) findViewById(R.id.txt_date);
        txtTime = (EditText) findViewById(R.id.txt_time);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        id = getIntent().getStringExtra(DrawerActivity.TAG_ID);
        title = getIntent().getStringExtra(DrawerActivity.TAG_TITLE);
        des = getIntent().getStringExtra(DrawerActivity.TAG_DES);
        date = getIntent().getStringExtra(DrawerActivity.TAG_DATE);
        timestamp = getIntent().getStringExtra(DrawerActivity.TAG_TIMESTAMP);

        if (id == null || id == "") {
            setTitle("Add Data");
        } else {
            setTitle("Edit Data");
            txt_id.setText(id);
            txt_title.setText(title);
            txt_des.setText(des);
            txtDate.setText(date);
            txtTime.setText(timestamp);
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (txt_id.getText().toString().equals("")) {
                        save();
                    } else {
                        edit();
                    }
                } catch (Exception e) {
                    Log.e("Submit", e.toString());
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blank();
                finish();
            }
        });

        txtDate = (EditText) findViewById(R.id.txt_date);
        txtTime = (EditText) findViewById(R.id.txt_time);
        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        txtDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                showDialog(DATE_DIALOG_ID);
                return true;
            }
        });

        txtTime.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                showDialog(TIME_DIALOG_ID);
                return true;
            }
        });

        switchStatus = (TextView) findViewById(R.id.mySwitch);
        mySwitch = (Switch) findViewById(R.id.mySwitch);

        //default switch//
        mySwitch.setChecked(false);

        //untuk merubah status//
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mySwitch.setText("Status : priority");
                }
                else
                {
                    mySwitch.setText("Status : no priority");
                }
            }

        });



    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(
                        this, mTimeSetListener, hour, minute, true);
            case DATE_DIALOG_ID:
                return new DatePickerDialog(
                        this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    String sdate = arrMonth[mMonth] + " " + LPad(mDay + "", "0", 2) + ", " + mYear;
                    txtDate.setText(sdate);
                }
            };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener()
            {
                public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour)
                {
                    hour = hourOfDay;
                    minute = minuteOfHour;
                    String stime = LPad(""+hour, "0", 2) + ":"+ LPad(""+hour, "0", 2);
                    txtTime.setText(stime);
                }
            };

    private static String LPad(String schar, String spad, int len) {
        String sret = schar;
        for (int i = sret.length(); i < len; i++) {
            sret = spad + sret;
        }
        return new String(sret);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Make blank all Edit Text
    private void blank() {
        txt_title.requestFocus();
        txt_id.setText(null);
        txt_title.setText(null);
        txt_des.setText(null);
    }


    // Save data to SQLite database
    private void save() {
        if (String.valueOf(txt_title.getText()).equals(null) || String.valueOf(txt_title.getText()).equals("") ||
                String.valueOf(txt_des.getText()).equals(null) || String.valueOf(txt_des.getText()).equals("") ||
                String.valueOf(txtDate.getText()).equals(null) || String.valueOf(txtDate.getText()).equals("") ||
                String.valueOf(txtTime.getText()).equals(null) || String.valueOf(txtTime.getText()).equals("") ||
                String.valueOf(txtTime.getText()).equals(null) || String.valueOf(txtTime.getText()).equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please input name or address ...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.insert(txt_title.getText().toString().trim(), txt_des.getText().toString().trim(),  txtDate.getText().toString().trim(),  txtTime.getText().toString().trim());
            blank();
            finish();
            Toast.makeText(getApplicationContext(),
                    "Data saved!", Toast.LENGTH_SHORT).show();
        }
    }
    // Update data in SQLite database
    private void edit() {
        if (String.valueOf(txt_title.getText()).equals(null) || String.valueOf(txt_title.getText()).equals("") ||
                String.valueOf(txt_des.getText()).equals(null) || String.valueOf(txt_des.getText()).equals("") ||
                String.valueOf(txtDate.getText()).equals(null) || String.valueOf(txtDate.getText()).equals("") ||
                String.valueOf(txtTime.getText()).equals(null) || String.valueOf(txtTime.getText()).equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please input name or address ...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.update(Integer.parseInt(txt_id.getText().toString().trim()), txt_title.getText().toString().trim(),
                    txt_des.getText().toString().trim(),  txtDate.getText().toString().trim(),  txtTime.getText().toString().trim());
            blank();
            finish();
        }
    }


}