package com.example.smartnote.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.smartnote.Views.activity.NewNote;

public class BootUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, NewNote.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
//        AppExecutors.getInstance().mainThread().execute(new Runnable() {
//            @Override
//            public void run() {
//                context.startService(new Intent(context, AlarmSoundService.class));
//            }
//        });

        //TODO Stop Sound Service to play sound alarm
//        context.startService(new Intent(context, AlarmSoundService.class));
    }
}
