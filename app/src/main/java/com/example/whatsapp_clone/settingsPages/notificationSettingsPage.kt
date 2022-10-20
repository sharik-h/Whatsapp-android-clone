package com.example.whatsapp_clone.settingsPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.ui.theme.green60
import com.example.whatsapp_clone.ui.theme.grey60

@Composable
fun notificationSettingsPage() {
    val arrowImg = painterResource(id = R.drawable.arrow_back)
    val optionsImg = painterResource(id = R.drawable.option_img)

    val list1 = "Conversation tones" to "play sounds for incoming and outgoing messages."
    val list2 = listOf(
        ("Notification tone" to "Default ringtone (Encounter)" )to false,
        ("Vibrate" to "Default" )to false,
        ("Popup notification" to "Not available" )to false,
        ("Light" to "White" )to false,
        ("Use high priority notifications" to "Show previews of notifications at the top of the screen") to true,
        ("Reaction Notification" to "show notification for reactions to messages you send") to true
    )
    val list3 = listOf(
        "Ringtone" to "Default ringtone (Jovi Lifestyle)",
        "Vibrate" to "Default"
    )


    var menuVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            elevation = 0.dp,
            backgroundColor = green60
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = arrowImg, contentDescription = "")
            }
            Text(
                text = "Notifications",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.weight(0.7f)
            )
            IconButton(onClick = { menuVisible = !menuVisible }) {
                Icon(painter = optionsImg, contentDescription = "", tint = Color.White )
                DropdownMenu(
                        expanded = menuVisible,
                onDismissRequest = { menuVisible = !menuVisible },
                ) {
                    TextButton(
                        onClick = { menuVisible = !menuVisible },
                        modifier = Modifier.width(260.dp).height(40.dp),
                    ) {
                        Text(
                            text = "Reset notification settings",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            modifier = Modifier.clickable { }
                        )
                    }
            }
            }
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
            notificationOptions(Name = list1.first, info = list1.second, radio = true)
            Divider(thickness = 0.5.dp)
            Text(
                text = "Messages",
                fontWeight = FontWeight.Bold,
                color = grey60,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
            )
            list2.forEach {
                notificationOptions(Name = it.first.first, info = it.first.second, radio = it.second)
            }
            Divider(thickness = 0.5.dp)
            Text(text = "Groups",
                fontWeight = FontWeight.Bold,
                color = grey60,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
            list2.forEach {
                notificationOptions(Name = it.first.first, info = it.first.second, radio = it.second)
            }
            Divider(thickness = 0.5.dp)
            Text(
                text = "Calls",
                fontWeight = FontWeight.Bold,
                color = grey60,
                modifier = Modifier.padding(start = 20.dp, top = 20.dp)
            )
            list3.forEach {
                notificationOptions(Name = it.first, info = it.second, radio = false)
            }
        }
    }
}


@Composable
fun notificationOptions(
    Name: String,
    info: String,
    radio: Boolean
) {
    var checked by remember{mutableStateOf(false)}
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(19.dp)
            .clickable {
                if (radio) checked = !checked
            }
    ) {
        Column(Modifier.weight(0.7f)) {
            Text(text = Name, fontSize = 15.sp)
            Text(text = info, color = grey60)
        }
        if (radio) Switch(checked = checked, onCheckedChange = {checked = !checked})
    }
}