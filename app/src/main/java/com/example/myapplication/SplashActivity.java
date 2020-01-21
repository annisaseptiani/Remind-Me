package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
    TextView tvSplash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity);

        tvSplash = (TextView) findViewById(R.id.tvSplash);


    final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(getApplicationContext(), DrawerActivity.class));
            finish();
        }
    }, 2000L); //3000 L = 3 detik
}
}


