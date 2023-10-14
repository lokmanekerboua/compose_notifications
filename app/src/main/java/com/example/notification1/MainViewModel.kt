package com.example.notification1

import android.annotation.SuppressLint
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notification1.di.MainNotificationBuilder
import com.example.notification1.di.SecondNotificationBuilder
import com.example.notification1.di.ThirdNotificationBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

    private val notificationManager: NotificationManagerCompat,
    @MainNotificationBuilder
    private val notificationBuilder: NotificationCompat.Builder,
    @SecondNotificationBuilder
    private val notificationBuilder2: NotificationCompat.Builder,

    @ThirdNotificationBuilder
    private val notificationBuilder3: NotificationCompat.Builder

) : ViewModel() {
    @SuppressLint("MissingPermission")
    fun showSimpleNotification() {
        notificationManager.notify(1, notificationBuilder.build())
    }

    @SuppressLint("MissingPermission")
    fun updateNotification() {
        notificationBuilder.setContentText("Updated Content")
        notificationManager.notify(
            1,
            notificationBuilder.setContentText("this is the message from the update").build()
        )

    }

    @SuppressLint("MissingPermission")
    fun messageNotification() {
        notificationManager.notify(1, notificationBuilder3.build())
    }

    fun cancelNotification() {
        notificationManager.cancel(1)
    }

    @SuppressLint("MissingPermission")
    fun showProgress() {
        val Max = 10
        var progress = 0

        viewModelScope.launch {
            while (progress != Max) {
                delay(1000)
                progress += 1
                notificationManager.notify(
                    3,
                    notificationBuilder2
                        .setContentTitle("Downloading")
                        .setContentText("${progress}/${Max}")
                        .setProgress(Max, progress, false)
                        .build()
                )
            }

            notificationManager.notify(
                3,
                notificationBuilder
                    .setContentTitle("Download Complete!")
                    .setContentText("")
                    .setContentIntent(null)
                    .clearActions()
                    .setProgress(0, 0, false)
                    .build()
            )
        }
    }
}
