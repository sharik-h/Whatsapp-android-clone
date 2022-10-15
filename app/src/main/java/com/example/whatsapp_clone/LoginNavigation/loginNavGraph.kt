package com.example.whatsapp_clone.LoginNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.whatsapp_clone.loginPages.agreeTerms
import com.example.whatsapp_clone.loginPages.numberPage
import com.example.whatsapp_clone.splashScreen.Splash

@Composable
fun loginNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = loginScreen.splashScreen.route
    ) {
     composable(route = loginScreen.splashScreen.route) {
         Splash(navHostController)
     }
     composable(route = loginScreen.agreeTerms.route ){
         agreeTerms(navHostController)
     }
     composable(route = loginScreen.numberPage.route) {
         numberPage()
     }
    }
}