package com.example.hppc.sms_app;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Compose extends AppCompatActivity {

    private TextView phone_to;
    private EditText phone_no, message;
    private View cmpLayout;
    private static final int REQUEST_SEND_SMS = 1;
    private Button Send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        phone_to = (TextView) findViewById(R.id.textView);
        phone_no = (EditText) findViewById(R.id.phone_no);
        message = (EditText) findViewById(R.id.message);
        cmpLayout = (View) findViewById(R.id.cmp_root);

        Send = (Button) findViewById(R.id.send);

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no = phone_no.getText().toString();
                String mess = message.getText().toString();
                if (no.length() > 0 && mess.length() > 0) {
                    SendMsg(no, mess);
                } else {
                    Snackbar.make(cmpLayout, "Enter Phone Number & Message", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

    }

    public void SendMsg(String no, String mess) {


        try {
            SmsManager sm = SmsManager.getDefault();
            sm.sendTextMessage(no, null, mess, null, null);

            Snackbar.make(cmpLayout, "Message Sent Successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(cmpLayout, "Message Not Sent", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        }
    }

    public void permissionSendCheck() {

        if (ContextCompat.checkSelfPermission(Compose.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestSMSSendPermission();
        } else {
        }
    }

    public void requestSMSSendPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(Compose.this,
                Manifest.permission.SEND_SMS)) {

            Snackbar.make(cmpLayout, "Permision to read SMS is required", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(Compose.this,
                                    new String[]{com.example.hppc.sms_app.Manifest.permission.SEND_SMS}, REQUEST_SEND_SMS);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(Compose.this,
                    new String[]{com.example.hppc.sms_app.Manifest.permission.READ_SMS}, REQUEST_SEND_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Snackbar.make(cmpLayout, "Message Not Sent", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
            return;
        }
    }
}
