package com.example.smartnote.Notification;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import static androidx.legacy.content.WakefulBroadcastReceiver.startWakefulService;

public class RemainderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //TODO Stop Sound Service to play sound alarm
        context.startService(new Intent(context, AlarmSoundService.class));

        //TODO This will send a notification message and show notification in notification tray
        ComponentName comp = new ComponentName(context.getPackageName(),
                NotificationService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));

    }

}
