package com.example.smartnote.Views.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.smartnote.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
//        new Handler().postDelayed((Runnable) this,2000);
        new Handler().postDelayed(() -> {
            Intent intent=new Intent(Splash.this, SmartNote.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
    }