package com.example.whatsapp_clone.settingNavigation

sealed class settingsScreen(val route: String) {
    object mainSetting: settingsScreen(route = "mainSettings")
    object profileSetting: settingsScreen(route = "profileSetting")
    object accountSetting: settingsScreen(route = "accountSetting")
    object chatSetting: settingsScreen(route = "chatSetting")
    object notificationSetting: settingsScreen(route = "notificationSetting")
    object storageSetting: settingsScreen(route = "storageSetting")
    object helpSetting: settingsScreen(route = "helpSetting")
    object inviteSettting: settingsScreen(route = "inviteSetting")
}