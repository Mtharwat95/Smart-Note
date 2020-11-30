package com.example.smartnote.Views.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.smartnote.R;

public class MyProfile extends AppCompatActivity implements View.OnClickListener {

    androidx.constraintlayout.utils.widget.ImageFilterView close;
    com.google.android.material.textview.MaterialTextView toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        close = findViewById(R.id.close);
        toolbar = findViewById(R.id.toolbarTitle);
        toolbar.setText("My Profile");
        close.setVisibility(View.VISIBLE);
        close.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == close){
            finish();
        }
    }
}