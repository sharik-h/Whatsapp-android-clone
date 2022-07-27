package com.example.whatsapp_clone.modelItems

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

@Preview(showBackground = true)
@Composable
fun chatItemModel() {
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
                    Text(text = "Name", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(0.75f))
                    Text(text = "7/23/22", color = Color(0xFF808080), fontSize = 13.sp)
                    Spacer(modifier = Modifier.width(15.dp))
                }
                Text(text = "hello", color = Color(0xFF808080))
            }
        }
    }
}