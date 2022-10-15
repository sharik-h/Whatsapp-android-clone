package com.example.whatsapp_clone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.whatsapp_clone.LoginNavigation.loginNavGraph
import com.example.whatsapp_clone.ui.theme.Whatsapp_cloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Whatsapp_cloneTheme {
                val navHostController = rememberNavController()
                loginNavGraph(navHostController = navHostController)
            }
        }
    }
}