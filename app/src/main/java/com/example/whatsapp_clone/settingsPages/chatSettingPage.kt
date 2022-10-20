package com.example.whatsapp_clone.settingsPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.ui.theme.green60
import com.example.whatsapp_clone.ui.theme.grey60

@Composable
fun chatSettingPage() {
    val arrowImg = painterResource(id = R.drawable.arrow_back)
    val themeImg = painterResource(id = R.drawable.ic_baseline_brightness_6_24)
    val wallpaperImg = painterResource(id = R.drawable.ic_baseline_wallpaper_24)
    val appLangImg = painterResource(id = R.drawable.ic_outline_language_24)
    val backupImg = painterResource(id = R.drawable.ic_baseline_backup_24)
    val historyImg = painterResource(id = R.drawable.ic_baseline_history_24)

    val list1 = listOf(
        themeImg to "Theme",
        wallpaperImg to "Wallpaper"
    )
    val list2 = listOf(
        "Enter is send" to "Enter key will send your messages" to true,
        "Media visibility" to "Show newly downloaded media in your phone's gallery" to true,
        "Font size" to "Large" to false
    )
    val list3 = "Keep cahts archived" to "Archived chats will remain archived when you receive a new message"
    val list4 = listOf(
        appLangImg to "App language",
        backupImg to "Chat backup",
        historyImg to "Chat history"
    )

    Column(Modifier.fillMaxSize()) {
        TopAppBar(backgroundColor = green60) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = arrowImg, contentDescription = "")
            }
            Text(text = "Chats", color = Color.White, fontSize = 20.sp)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {


            Text(
                text = "Display",
                fontWeight = FontWeight.Bold,
                color = grey60,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
            list1.forEach {
                settingOptions(Icon = it.first, Name = it.second, radio = false, info = null)
            }
            Divider(thickness = 0.5.dp)
            Text(
                text = "Chat settings",
                fontWeight = FontWeight.Bold,
                color = grey60,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
            list2.forEach {
                settingOptions(Icon = null, Name = it.first.first, info = it.first.second, radio = it.second)
            }

            Divider(thickness = 0.5.dp)
            Text(
                text = "Archived chats",
                fontWeight = FontWeight.Bold,
                color = grey60,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
            settingOptions(Icon = null, Name = list3.first, info = list3.second, radio = true)
            Divider(thickness = 0.5.dp)
            list4.forEach {
                settingOptions(Icon = it.first, Name = it.second, info = null, radio = false)
            }
            Divider(thickness = 0.5.dp)
        }
    }
 }


@Composable
fun settingOptions(
    Icon: Painter?,
    Name: String,
    info: String?,
    radio: Boolean
) {
    var checked by remember{mutableStateOf(false)}

 Row(
     modifier = Modifier
         .fillMaxSize()
         .padding(20.dp)
         .clickable{if (radio){ checked = !checked }}
 ) {
     Icon?.let { Image(painter = Icon, contentDescription = "") }
     var width = 30
     if (Icon == null ) width = 55
     Spacer(modifier = Modifier.width(width.dp))
     Column(Modifier.weight(0.7f)) {
         Text(text = Name, fontSize = 15.sp)
         info?.let {
             Text(text = info, color = grey60)
         }

     }
     if (radio) Switch(checked = checked, onCheckedChange = {checked = !checked})
 }
}