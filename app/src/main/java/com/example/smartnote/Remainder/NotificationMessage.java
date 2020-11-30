package com.example.smartnote.Remainder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartnote.R;

public class NotificationMessage extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_message);
        textView = findViewById(R.id.tv_message);
        Bundle bundle = getIntent().getExtras();
        textView.setText(bundle.getString("message"));
        textView.setText(R.string.smart_note);
//        startService(new Intent(this, AlarmSoundService.class));
    }
}
