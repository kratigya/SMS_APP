package com.example.hppc.app_buyhatke;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.format.DateFormat;

import java.util.Date;

/**
 * Created by hppc on 30-Apr-16.
 */
public class Receiver extends BroadcastReceiver {

    private static final int MY_NOTIFICATION_ID = 1;
    public static final String SMS_BUNDLE = "pdus";
    private int notifCount;
    private Intent notifIntent;
    private PendingIntent conIntent;
    private long[] vibPattern = {0, 200, 200, 300};


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        Message new_msg;
        notifIntent = new Intent(context.getApplicationContext(), MainActivity.class);
        conIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bundle intentExtras = intent.getExtras();
        SmsMessage[] msg;
        String smsMessageStr = "";
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);

            msg = new SmsMessage[sms.length];

            for (int i = 0; i < msg.length; ++i) {
                new_msg = new Message();
                String format = intentExtras.getString("format");
                msg[i] = SmsMessage.createFromPdu((byte[]) sms[i], format);

                String smsBody = msg[i].getMessageBody().toString();
                String add = msg[i].getOriginatingAddress();
                long rectime = msg[i].getTimestampMillis();
                String date = DateFormat.format("MMMM dd, yyyy", new Date(rectime)).toString();

                Notification.Builder notificationBuilder = new Notification.Builder(context.getApplicationContext())
                        .setAutoCancel(true)
                        .setSmallIcon(android.R.drawable.stat_notify_chat)
                        .setContentTitle("New Message")
                        .setContentText(add + "  " + smsBody)
                        .setContentIntent(conIntent)
                        .setVibrate(vibPattern);

                NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notifManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());
            }
        }
    }
}