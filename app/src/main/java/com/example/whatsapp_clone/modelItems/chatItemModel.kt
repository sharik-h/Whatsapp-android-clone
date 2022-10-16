package com.example.whatsapp_clone.modelItems

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.chatActivity.chatActivity
import com.example.whatsapp_clone.data.detailFormat
import com.example.whatsapp_clone.data.userDetailsFormat
import com.example.whatsapp_clone.ui.theme.grey40
import com.example.whatsapp_clone.ui.theme.grey60
import com.example.whatsapp_clone.ui.theme.lightGreen

@Composable
fun chatItemModel(detailFormat: userDetailsFormat, notification: Int) {
    val imageIcon = painterResource(id = R.drawable.circle_img)
    val context = LocalContext.current
    Column(
       modifier = Modifier
           .fillMaxWidth()
           .height(80.dp)
           .clickable {
               context.startActivity(Intent(context, chatActivity::class.java)
                   .putExtra("name", detailFormat.name)
                   .putExtra("phone", detailFormat.phone)
                   .putExtra("msgdate", detailFormat.msgdate)
               )
           }) {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(11.dp))
            if (detailFormat.image == null) {
                Image(
                    painter = imageIcon,
                    contentDescription = "",
                    modifier = Modifier.size(60.dp)
                )
            }else {
                Image(
                    painter = rememberAsyncImagePainter(detailFormat.image),
                    contentDescription = "",
                    modifier = Modifier.clip(RoundedCornerShape(50)).size(50.dp)
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = detailFormat.name.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(0.75f)
                    )
                   detailFormat.msgdate?.let {
                       Text(
                           text = detailFormat.msgdate.toString(),
                           color = grey60,
                           fontSize = 13.sp
                       )
                   }
                    Spacer(modifier = Modifier.width(15.dp))
                }
                Row(Modifier.fillMaxWidth()) {
                    detailFormat.lastmsg?.let {
                        Text(
                            text = detailFormat.lastmsg.toString(),
                            color = grey60,
                            modifier = Modifier.weight(0.76f)
                        )
                    }
                    if (notification != 0) {
                        Box(
                            contentAlignment = Alignment.Center,
                          modifier = Modifier
                              .size(20.dp)
                              .clip(RoundedCornerShape(50))
                              .background(lightGreen)) {
                          Text(
                              text = notification.toString(),
                              color = Color.White,
                              fontSize = 12.sp
                          )
                        }
                    }
                    Spacer(modifier = Modifier.weight(0.04f))
                }
            }
        }
    }
}