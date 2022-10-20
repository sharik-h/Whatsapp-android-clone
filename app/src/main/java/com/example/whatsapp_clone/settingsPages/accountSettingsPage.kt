package com.example.whatsapp_clone.settingsPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.ui.theme.green60

@Composable
fun accountSettingsPage() {
    val arrowImg = painterResource(id = R.drawable.arrow_back)
    val privacyImg = painterResource(id = R.drawable.lock_img)
    val securityImg = painterResource(id = R.drawable.security_img)
    val twoStepVrifyImg = painterResource(id = R.drawable.two_step_verify_img)
    val changeNumImg = painterResource(id = R.drawable.smartphone_img)
    val requestInfoImg = painterResource(id = R.drawable.sticky_note_img)
    val deleteImg = painterResource(id = R.drawable.delete_img)
    
    val availableSettings = listOf(
        privacyImg to "Privacy",
        securityImg to "Security",
        twoStepVrifyImg to "Two-step verification",
        changeNumImg to "Change number",
        requestInfoImg to "Request account info",
        deleteImg to "Delete my account" 
    )
    
    Column() {
        TopAppBar(
            elevation = 0.dp,
            backgroundColor = green60
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = arrowImg, contentDescription = "")
            }
            Text(text = "Account", color = Color.White, fontSize = 20.sp)
        }
        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 5.dp)) {
            items(items = availableSettings) {
                settingsButton(icon = it.first , Name = it.second )
            }
        }
    }
}

@Composable
fun settingsButton(
    icon: Painter,
    Name: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal =  20.dp, vertical = 18.dp)) {
        Image(painter = icon, contentDescription = "", Modifier.size(23.dp))
        Spacer(modifier = Modifier.width(30.dp))
        Text(text = Name, fontSize = 16.sp)
    }
}