package com.example.whatsapp_clone

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.whatsapp_clone.settingNavigation.settingsNavGraph

class settingActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permissionRequest = ActivityCompat.checkSelfPermission(this,  android.Manifest.permission.READ_CONTACTS)
        val permissionGranted = PackageManager.PERMISSION_GRANTED
        if ( permissionRequest != permissionGranted) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS), 101 )
        }
        setContent {
            val navHostController = rememberNavController()
            settingsNavGraph(navHostController = navHostController)
        }
    }
}