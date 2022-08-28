package com.example.whatsapp_clone.modelItems

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.data.detailFormat

@Composable
fun chatItemModel(detailFormat: detailFormat) {
    val imageIcon = painterResource(id = R.drawable.circle_img)
    Column(
       modifier = Modifier
           .fillMaxWidth()
           .height(80.dp)) {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(11.dp))
            Image(painter = imageIcon, contentDescription = "", modifier = Modifier.size(60.dp))
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Row(Modifier.fillMaxWidth()) {
                    Text(text = detailFormat.name.toString(), fontSize = 18.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(0.75f))
                    Text(text = detailFormat.msgdate.toString(), color = Color(0xFF808080), fontSize = 13.sp)
                    Spacer(modifier = Modifier.width(15.dp))
                }
                Text(text = detailFormat.lastmsg.toString(), color = Color(0xFF808080))
            }
        }
    }
}