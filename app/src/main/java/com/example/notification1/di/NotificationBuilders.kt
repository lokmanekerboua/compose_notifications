package com.example.notification1.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PRIVATE
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import androidx.core.net.toUri
import com.example.notification1.MainActivity
import com.example.notification1.R
import com.example.notification1.navigation.MY_ARG
import com.example.notification1.navigation.MY_URI
import com.example.notification1.reciever.MessageReceiver
import com.example.notification1.reciever.MyReciever
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

const val RESULT_KEY = "result_key"

@Module
@InstallIn(SingletonComponent::class)
object NotificationBuilders {

    @Provides
    @Singleton
    @ThirdNotificationBuilder
    fun provideNotificatioMessageBuilder(@ApplicationContext context: Context): NotificationCompat.Builder {
        val remoteInput = RemoteInput.Builder(RESULT_KEY).setLabel("Reply...").build()

        val replyintent = Intent(context, MessageReceiver::class.java)

        val pendingReplyIntent = PendingIntent.getBroadcast(
            context,
            1,
            replyintent,
            PendingIntent.FLAG_MUTABLE
        )
        val replyAction = NotificationCompat.Action.Builder(
            0,
            "Reply",
            pendingReplyIntent
        )
            .addRemoteInput(remoteInput)
            .build()

        val person = Person.Builder().setName("islam").build()
        val notificationstyle = NotificationCompat.MessagingStyle(person)
            .addMessage("Hello there!", System.currentTimeMillis(), person)

        return NotificationCompat.Builder(context, "Reply")
            .setSmallIcon(R.drawable.alarm)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOnlyAlertOnce(true)
            .setStyle(notificationstyle)
            .addAction(replyAction)
    }


    @Provides
    @Singleton
    @MainNotificationBuilder
    fun provideNotificationBuilder(@ApplicationContext context: Context): NotificationCompat.Builder {

        val intent = Intent(context, MyReciever::class.java).apply {
            putExtra("message", "You reply to the message")
        }

        val flag = PendingIntent.FLAG_IMMUTABLE

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            flag
        )

        val clickintent = Intent(
            Intent.ACTION_VIEW,
            "$MY_URI/$MY_ARG= Comming from notification".toUri(),
            context,
            MainActivity::class.java
        )
        val clickPendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(clickintent)
            getPendingIntent(1, flag)
        }

        return NotificationCompat.Builder(context, "Messages")
            .setSmallIcon(R.drawable.alarm)
            .setContentTitle("Message")
            .setContentText("This is a NOTIFICATION: Hello World!")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVisibility(VISIBILITY_PRIVATE)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPublicVersion(
                NotificationCompat.Builder(context, "Messages")
                    .setSmallIcon(R.drawable.alarm)
                    .setContentTitle("Hiden")
                    .setContentText("Unlock to see messages")
                    .build()
            )
            .addAction(0, "ACTION", pendingIntent)
            .setContentIntent(clickPendingIntent)
    }

    @Provides
    @Singleton
    @SecondNotificationBuilder
    fun provideDownloadNotificationBuilder(@ApplicationContext context: Context): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, "Download")
            .setSmallIcon(R.drawable.alarm)
            .setContentTitle("Download")
            .setContentText("Downloading...")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
    }

    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        val channel = NotificationChannel(
            "Messages",
            "Messages",
            NotificationManager.IMPORTANCE_HIGH
        )
        val channel2 = NotificationChannel(
            "Download",
            "Download",
            NotificationManager.IMPORTANCE_LOW
        )

        val channel3 = NotificationChannel(
            "Reply",
            "Reply",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
        notificationManager.createNotificationChannel(channel2)
        notificationManager.createNotificationChannel(channel3)

        return notificationManager
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainNotificationBuilder

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SecondNotificationBuilder

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ThirdNotificationBuilder