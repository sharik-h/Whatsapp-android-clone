package com.example.whatsapp_clone.callDetailActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.R

class callDetailActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            callDetails()
        }
    }

    @Composable
    fun callDetails() {
        Column(Modifier.fillMaxWidth()) {
            val backArrowImg = painterResource(id = R.drawable.arrow_back)
            val chatImg = painterResource(id = R.drawable.chat_img_whilte)
            val optionImg = painterResource(id = R.drawable.option_img)
            val phoneImg = painterResource(id = R.drawable.phone_green)
            val cameraImg = painterResource(id = R.drawable.video_camera)
            val outGoingCallImg = painterResource(id = R.drawable.call_made)
            val incomingGoingCallImg = painterResource(id = R.drawable.call_received)
            val userImg = painterResource(id = R.drawable.circle_img)
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                elevation = 0.dp,
                backgroundColor = Color(0xFF008F6D)
            ) {
                IconButton(onClick = { finish() }) {
                    Image(painter = backArrowImg, contentDescription = "")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "Call info",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.weight(0.5f))
                IconButton(onClick = { /*TODO*/ }) {
                    Image(painter = chatImg, contentDescription = "")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Image(painter = optionImg, contentDescription = "")
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 10.dp)
            ) {
                Image(painter = userImg, contentDescription = "", modifier = Modifier.size(60.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        "Sample text",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp
                    )
                    Text("sample subtext", color = Color(0xFF919191))
                }
                Spacer(modifier = Modifier.weight(0.5f))
                IconButton(onClick = { /*TODO*/ }) {
                    Image(painter = phoneImg, contentDescription = "")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Image(painter = cameraImg, contentDescription = "")
                }
            }
            Divider(thickness = 1.dp, modifier = Modifier.padding(start = 70.dp))
        }
    }
}
