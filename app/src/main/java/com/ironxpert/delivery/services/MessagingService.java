package com.ironxpert.delivery.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ironxpert.delivery.R;
import com.ironxpert.delivery.common.auth.Auth;
import com.ironxpert.delivery.utils.Promise;

import java.util.Random;

public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        if (message.getNotification() != null) {
            pushNotification(message.getNotification().getTitle(), message.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        if (Auth.isUserAuthenticated(this)) {
            Auth.Login.updateMessageToken(Auth.getAuthUserUid(), token);
        }
    }

    private void pushNotification(String title, String body) {
        final String CHANNEL_ID = "RotiKing " + new Random().nextInt(100);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH);

        channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), Notification.AUDIO_ATTRIBUTES_DEFAULT);

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID).setContentText(title).setContentTitle(body).setSmallIcon(R.drawable.logo);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(new Random().nextInt(100), notification.build());
    }
}
