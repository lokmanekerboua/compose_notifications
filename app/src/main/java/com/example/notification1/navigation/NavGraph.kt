package com.example.notification1.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.notification1.screens.DetailsScreen
import com.example.notification1.screens.MainScreen

const val MY_ARG = "message"
const val MY_URI = "https://lokmvne.com"

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.route,
        route = "NavGraph"
    ) {
        composable(
            route = Screens.MainScreen.route
        ) {
            MainScreen(navController)
        }

        composable(
            route = Screens.DetailScreen.route,
            arguments = listOf(navArgument(MY_ARG) { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink { uriPattern = "$MY_URI/$MY_ARG={$MY_ARG}" })
        ) {
            val arguments = it.arguments
            arguments?.getString(MY_ARG)?.let { message ->
                DetailsScreen(message, navController)
                Log.d("NavGraph", "message: $message")
            }
        }
    }
}