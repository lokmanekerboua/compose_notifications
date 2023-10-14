package com.example.notification1.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyReciever : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("message")
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}