package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    ImageView imgAbout;
    ImageView imgReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        imgAbout = (ImageView) findViewById(R.id.imgAbout);
        imgAbout.setOnClickListener(this);
        imgReminder = (ImageView) findViewById(R.id.imgReminder);
        imgReminder.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAbout:
                Intent moveToAbout = new Intent(MainMenu.this, AboutActivity.class);
                startActivity(moveToAbout);
                break;
            case R.id.imgReminder:
                Intent moveToReminder = new Intent(MainMenu.this, ReminderActivity.class);
                startActivity(moveToReminder);
        }
    }
}
