package com.example.whatsapp_clone.LoginNavigation

sealed class loginScreen(val route: String) {
    object splashScreen: loginScreen(route = "splashScreen")
    object agreeTerms: loginScreen(route = "agreeTerms")
    object numberPage: loginScreen(route = "numberPage")
}
