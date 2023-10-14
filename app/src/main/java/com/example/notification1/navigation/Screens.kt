package com.example.notification1.navigation

sealed class Screens(val route: String) {
    object MainScreen : Screens(route = "main")
    object DetailScreen : Screens(route = "detail/{$MY_ARG}") {
        fun passArgument(message: String) = "detail/$message"
    }
}
