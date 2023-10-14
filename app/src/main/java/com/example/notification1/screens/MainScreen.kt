package com.example.notification1.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notification1.MainViewModel
import com.example.notification1.navigation.Screens

@Composable
fun MainScreen(
    navController: NavHostController, mainViewModel: MainViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = mainViewModel::showSimpleNotification
        ) {
            Text(text = "Simple notification")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = mainViewModel::updateNotification
        ) {
            Text(text = "Update Notification")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = mainViewModel::cancelNotification
        ) {
            Text(text = "Cancel Notification")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = mainViewModel::showProgress
        ) {
            Text(text = "Download Notification")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = mainViewModel::messageNotification
        ) {
            Text(text = "Message Notification")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            navController.navigate(Screens.DetailScreen.passArgument("Comming from from MainScreen"))
        }) {
            Text(text = "Open Details Screen")
        }
    }
}