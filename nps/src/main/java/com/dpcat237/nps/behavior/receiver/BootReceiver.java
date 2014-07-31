package com.dpcat237.nps.behavior.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * This BroadcastReceiver automatically (re)starts the alarm when the device is
 * rebooted. This receiver is set to be disabled (android:enabled="false") in the
 * application's manifest file. When the user sets the alarm, the receiver is enabled.
 * When the user cancels the alarm, the receiver is disabled, so that rebooting the
 * device will not trigger this receiver.
 */
public class BootReceiver extends BroadcastReceiver {
    private AlarmSyncNewsReceiver alarmSyncNews = new AlarmSyncNewsReceiver();
    private AlarmSyncDictationsReceiver alarmSyncDictations = new AlarmSyncDictationsReceiver();
    private AlarmRemoveOldReceiver alarmRemoveOld = new AlarmRemoveOldReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            alarmSyncNews.setAlarm(context);
            alarmSyncDictations.setAlarm(context);
            alarmRemoveOld.setAlarm(context);
        }
    }
}