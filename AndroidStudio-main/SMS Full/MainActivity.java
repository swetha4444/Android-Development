package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.SEND_SMS;
public class MainActivity extends AppCompatActivity {
    EditText contact_no,user_message;
    Button send;
    NotificationManager manager;
    Notification myNotication;
    int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,new
                String[]{READ_SMS,RECEIVE_SMS,SEND_SMS,READ_PHONE_STATE},1);
        contact_no = findViewById(R.id.contact_number);
        user_message = findViewById(R.id.user_message);
        send = findViewById(R.id.sendButton);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cnt <2)
                    sendMsg(contact_no.getText().toString(), user_message.getText().toString());
                else
                {
                    //Exit Activity
                    Toast etoast = Toast.makeText(MainActivity.this,"SMS Overloaded",Toast.LENGTH_SHORT);
                    etoast.show();
                    finish();
                    moveTaskToBack(true);
                }
            }
        });

    }

    void sendMsg(String number, String message)
    {
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(number, null, message, null, null);
        Toast toast = Toast.makeText(MainActivity.this,"SMS Recieved",Toast.LENGTH_SHORT);
        toast.show();
        cnt+=1;
        //Status Bar notification
        sendNotif(message);
    }


    void sendNotif(String message) {
        try {
            //API 24
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setContentTitle("Message Alert !");
            builder.setContentText(message);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());
        }catch(Exception e){e.printStackTrace();}
    }

}
