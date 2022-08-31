package com.example.whatsapp_clone.mainPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
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
fun statusPage() {
    Column(Modifier.fillMaxSize()) {
        val userImg = painterResource(id = R.drawable.circle_img)
        val addImg = painterResource(id = R.drawable.add_circle)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(5.dp))
            Box(Modifier.size(60.dp)) {
                Image(
                    painter = userImg,
                    contentDescription = "",
                    Modifier.size(60.dp)
                )
                Image(
                    painter = addImg,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 32.dp, start = 32.dp)
                        .size(25.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "My status",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(text = "Tap to add status updates", color = Color(0xFF616161))
            }
        }
        Text(
            text = "Recent updates",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 15.dp),
            color = Color(0xFF616161)
        )
    }
}
