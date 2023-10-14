package com.example.notification1.reciever

import android.annotation.SuppressLint
import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import com.example.notification1.di.RESULT_KEY
import com.example.notification1.di.ThirdNotificationBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MessageReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationmanager: NotificationManagerCompat

    @Inject
    @ThirdNotificationBuilder
    lateinit var notificationbuilder3: NotificationCompat.Builder

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            val input = remoteInput.getCharSequence(RESULT_KEY).toString()
            val person = Person.Builder().setName("lokmane").build()
            val message = NotificationCompat.MessagingStyle.Message(
                input, System.currentTimeMillis(), person
            )
            val notificationStyle = NotificationCompat.MessagingStyle(person)
                .addMessage(message)
            notificationmanager.notify(
                1,
                notificationbuilder3
                    .setStyle(notificationStyle)
                    .build()
            )
        }
    }
}
