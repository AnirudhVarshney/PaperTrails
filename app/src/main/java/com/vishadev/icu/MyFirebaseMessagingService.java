package com.vishadev.icu;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vishal on 28/11/16.
 */

public class MyFirebaseMessagingService  extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static  String loginuser;
    public static String  lat;
    public static String lon;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
     super.onMessageReceived(remoteMessage);


        Log.e("remotemessage",""+remoteMessage.toString());
        if (remoteMessage.getFrom().equals("/topics/"+ MyFirebaseInstanceIDService.infoTopicName));
        sendNotification(remoteMessage.getData().get("data"),remoteMessage.getData().get("content-text"));
    }




    private void sendNotification(String messageBody,String contentText) {

        Log.e("messagebody",messageBody.toString());
        pushnotification(messageBody);

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New Location")
                .setContentText(loginuser+" "+" "+lat+" "+" "+lon)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 , notificationBuilder.build());

    }

    public static void pushnotification(String messageBody) {
        try {
            JSONObject jsonObject = new JSONObject(messageBody);
           loginuser = jsonObject.getString("loginuser");
            lat = jsonObject.getString("lat");
            lon = jsonObject.getString("lon");
            Log.e("after",loginuser + lat +lon);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
