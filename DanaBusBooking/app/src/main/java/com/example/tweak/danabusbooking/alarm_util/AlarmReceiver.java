package com.example.tweak.danabusbooking.alarm_util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    // Called when this class is fired
    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context, "New Data has created", "Happy new day!", "Updated");
    }

    public void createNotification(Context context, String message, String messageText, String messageAlert) {
        // Change the state of the seats

        // Create new tickets
    }

}