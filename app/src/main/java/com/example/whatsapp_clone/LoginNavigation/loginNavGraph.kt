package com.example.whatsapp_clone.LoginNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.whatsapp_clone.loginPages.agreeTerms
import com.example.whatsapp_clone.loginPages.numberPage

@Composable
fun loginNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = loginScreen.agreeTerms.route
    ) {
     composable(route = loginScreen.agreeTerms.route ){
         agreeTerms(navHostController)
     }
     composable(route = loginScreen.numberPage.route) {
         numberPage()
     }
    }
}