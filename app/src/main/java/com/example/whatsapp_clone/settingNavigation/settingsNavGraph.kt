package com.example.whatsapp_clone.settingNavigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.whatsapp_clone.settingsPages.*

@Composable
fun settingsNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = settingsScreen.mainSetting.route )
    {
        composable(route = settingsScreen.mainSetting.route) {
            MainSettings(navHostController = navHostController)
        }
        composable(route = settingsScreen.profileSetting.route) {
            profileSettingsPage()
        }
        composable(route = settingsScreen.accountSetting.route) {
            accountSettingsPage()
        }
        composable(route = settingsScreen.chatSetting.route) {
            chatSettingPage()
        }
        composable(route = settingsScreen.notificationSetting.route) {
            notificationSettingsPage()
        }
        composable(route = settingsScreen.storageSetting.route) {
            storageSettingsPage()
        }
        composable(route = settingsScreen.helpSetting.route) {
            helpSettingsPage()
        }
        composable(route = settingsScreen.inviteSettting.route) {
            inviteFriendPage()
        }
    }
}