package com.example.whatsapp_clone.settingsPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.ui.theme.green60
import com.example.whatsapp_clone.ui.theme.grey60

@Preview(showBackground = true)
@Composable
fun helpSettingsPage() {
    val arrowImg = painterResource(id = R.drawable.arrow_back)
    val helpImg = painterResource(id = R.drawable.info_img)
    val contactImg = painterResource(id = R.drawable.people_img)
    val termsImg = painterResource(id = R.drawable.sticky_note_img)
    val infoImg = painterResource(id = R.drawable.about_img)

    val list1 = listOf(
        helpImg to "Help Center" to null,
        contactImg to "Contact us" to "Questions? Need help?",
        termsImg to "Terms and Privacy Policy" to null,
        null to "Yearly reminder of our Terms of Service" to null,
        infoImg to "App info" to null
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        TopAppBar(backgroundColor = green60) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = arrowImg, contentDescription = "")
            }
            Text(text = "Help", fontSize = 20.sp, color = Color.White)
        }
        list1.forEach {
           helpOptions(Icon = it.first?.first, Name = it.first?.second, info = it.second)
        }
    }
}
@Composable
fun helpOptions(
    Icon: Painter?,
    Name: String?,
    info: String?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .clickable {},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        Icon?.let { Image(painter = Icon, contentDescription = "") }
        var width = 30
        if (Icon == null ) width = 55
        Spacer(modifier = Modifier.width(width.dp))
        Column(Modifier.weight(0.7f)) {
           Name?.let {
                androidx.compose.material3.Text(text = Name, fontSize = 16.sp)
            }
            info?.let {
                androidx.compose.material3.Text(text = info, color = grey60)
            }
        }
    }
}