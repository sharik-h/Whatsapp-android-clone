package com.example.whatsapp_clone.newChatActivity

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.R

@Composable
fun chatDetailsPage(name: String, phone: String, lstseen: String?) {
    val backImg = painterResource(id = R.drawable.arrow_back_grey)
    val optionImg = painterResource(id = R.drawable.option_grey)
    val userImg = painterResource(id = R.drawable.circle_img)
    val phoneImg = painterResource(id = R.drawable.phone_green)
    val cameraImg = painterResource(id = R.drawable.video_camera)
    val searchImg = painterResource(id = R.drawable.search_green)
    val rupeeImg = painterResource(id = R.drawable.circlebg_img)
    val notificationImg = painterResource(id = R.drawable.notification_img)
    val musicImg = painterResource(id = R.drawable.music_grey)
    val galleryImg = painterResource(id = R.drawable.image_grey)
    val lockImg = painterResource(id = R.drawable.lock_img)
    val disappearingImg = painterResource(id = R.drawable.storage_img)
    val blockImg = painterResource(id = R.drawable.block_red)
    val reportImg = painterResource(id = R.drawable.thumb_down_red)


    Box(Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
                .verticalScroll(rememberScrollState())
        ) {


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(335.dp)
                    .background(Color.White)
            ) {

                Image(painter = userImg, contentDescription = "", modifier = Modifier.size(135.dp))
                Text(text = name, fontSize = 25.sp)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = phone, color = Color(0xFF797979), fontSize = 18.sp)
                Spacer(modifier = Modifier.height(5.dp))
                lstseen?.let{
                    Box(
                        modifier = Modifier
                            .background(color = Color(0xFFF7F7F7))
                            .height(24.dp)
                            .padding(top = 3.dp)
                    ) {
                        Text(text = " Last seen $lstseen ", color = Color(0xFF919191))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Row() {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = phoneImg,
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Audio",
                            color = Color(0xFF00A584),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.width(38.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = cameraImg,
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Video",
                            color = Color(0xFF00A584),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.width(38.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = searchImg,
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Search",
                            color = Color(0xFF00A584),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.width(38.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = rupeeImg,
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = " Pay",
                            color = Color(0xFF00A584),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(165.dp)
                    .background(Color.White)
            ) {
                ItemSample(
                    painterIcon = notificationImg,
                    optionTitle = "Mute notificaion",
                )
                ItemSample(
                    painterIcon = musicImg,
                    optionTitle = "Custom notification",
                )
                ItemSample(
                    painterIcon = galleryImg,
                    optionTitle = "Media visibility",
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.White)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                ItemSample(
                    painterIcon = lockImg,
                    optionTitle = "Encryption",
                    optionDiscription = "Messages and calls are end-to-end \nencrypted. Tap to verify"
                )
                ItemSample(
                    painterIcon = disappearingImg,
                    optionTitle = "Disappearing messages",
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(Color.White)
            ) {
                ItemSample(
                    painterIcon = blockImg,
                    optionTitle = "Block (name)",
                    isRed = true
                )
                ItemSample(
                    painterIcon = reportImg,
                    optionTitle = "Report (name)",
                    isRed = true
                )
            }
        }
    }
    TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
        IconButton(onClick = { /*TODO*/ }) {
            Image(painter = backImg, contentDescription = "")
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Image(painter = optionImg, contentDescription = "")
        }
    }
}

@Composable
fun ItemSample(
    painterIcon: Painter,
    optionTitle: String,
    optionDiscription: String = "",
    isRed: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clickable { },
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
            if (isRed) Text(text = optionTitle, fontSize = 15.sp, color = Color.Red)
            else Text(text = optionTitle, fontSize = 15.sp)
            if (optionDiscription != "") {
                Text(text = optionDiscription, color = Color(0xFF818584))
            }
        }
    }
}