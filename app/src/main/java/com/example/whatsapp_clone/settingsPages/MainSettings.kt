package com.example.whatsapp_clone.settingsPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.data.optionFormat
import com.example.whatsapp_clone.settingNavigation.settingsScreen

@Composable
fun MainSettings(navHostController: NavHostController) {
    val backArrowImg = painterResource(id = R.drawable.arrow_back)
    val userIcon = painterResource(id = R.drawable.circle_img)
    val barCodeImg = painterResource(id = R.drawable.qrcode_img)
    var metaImg = painterResource(id = R.drawable.meta_img)

    val optionList = listOf(
         optionFormat(icon = painterResource(id = R.drawable.key_img), name = "Account", discription = "Privacy,security,change number", navigation = settingsScreen.accountSetting.route),
        optionFormat(icon = painterResource(id = R.drawable.chat_img), name = "Chats", discription = "Theme,wallpapers,chat history", navigation = settingsScreen.chatSetting.route),
        optionFormat(icon = painterResource(id = R.drawable.notification_img), name = "Notification", discription = "Message,group & call history", navigation = settingsScreen.notificationSetting.route),
        optionFormat(icon = painterResource(id = R.drawable.storage_img), name = "Storage and data", discription = "Network usage, auto-download", navigation = settingsScreen.storageSetting.route),
        optionFormat(icon = painterResource(id = R.drawable.info_img), name = "Help", discription = "Help center, contact us, privacy policy", navigation = settingsScreen.helpSetting.route),
        optionFormat(icon = painterResource(id = R.drawable.people_img), name = "Invite a friend", discription = "", navigation = settingsScreen.inviteSettting.route),
    )

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            backgroundColor = Color(0xFF008268),
            elevation = 0.dp
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    painter = backArrowImg,
                    contentDescription = "",
                )
            }
            Text(text = "Settings", color = Color.White, fontSize = 20.sp)
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(95.dp)
            .clickable { navHostController.navigate(settingsScreen.profileSetting.route) },
            verticalAlignment = Alignment.CenterVertically, ) {
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = userIcon,
                contentDescription = "user profile photo",
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(Modifier.weight(0.5f)) {
                Text(text = "User", fontSize = 20.sp)
                Text(text = "Hey there! I a using Whatsapp.", color = Color(0xFF818584))
            }
            Image(painter = barCodeImg, contentDescription = "", Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(15.dp))
        }
        Divider(thickness = 0.5.dp)
        LazyColumn() {
            items(items = optionList) {
                settingSample(
                    painterIcon = it.icon,
                    optionTitle = it.name,
                    optionDiscription = it.discription,
                    onClik = { navHostController.navigate(it.navigation) }
                )
            }
        }

        Text(
            text = "from",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Image(painter = metaImg, contentDescription = "", modifier = Modifier
                .size(16.dp)
                .padding(top = 2.dp))
            Text(
                text = "  Meta",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun settingSample(
    painterIcon: Painter,
    optionTitle: String,
    optionDiscription: String,
    onClik: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .clickable { onClik() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        Image(
            painter = painterIcon,
            contentDescription = "",
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(30.dp))
        Column(Modifier.fillMaxWidth()) {
            Text(text = optionTitle, fontSize = 15.sp)
            Text(text = optionDiscription, color = Color(0xFF818584))
        }
    }
}