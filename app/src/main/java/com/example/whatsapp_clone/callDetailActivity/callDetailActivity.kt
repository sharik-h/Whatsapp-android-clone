package com.example.whatsapp_clone.callDetailActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.chatActivity.chatActivity
import com.example.whatsapp_clone.viewmodel.FirestoreViewModel

class callDetailActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel:FirestoreViewModel by viewModels()
        val name = intent.getStringExtra("name")
        val time = intent.getStringExtra("time")
        val phone = intent.getStringExtra("phone")
        setContent {
            callDetails(name!!, time!!, phone!!, viewModel)
        }
    }

    @Composable
    fun callDetails(name: String, time: String, phone: String, viewModel: FirestoreViewModel) {
        Column(Modifier.fillMaxWidth()) {
            val backArrowImg = painterResource(id = R.drawable.arrow_back)
            val chatImg = painterResource(id = R.drawable.chat_img_whilte)
            val optionImg = painterResource(id = R.drawable.option_img)
            val phoneImg = painterResource(id = R.drawable.phone_green)
            val cameraImg = painterResource(id = R.drawable.video_camera)
            val outGoingCallImg = painterResource(id = R.drawable.call_made)
            val incomingGoingCallImg = painterResource(id = R.drawable.call_received)
            val userImg = painterResource(id = R.drawable.circle_img)
            val context = LocalContext.current
            val loadUserImg = viewModel.loadImageBitmap(name1 = phone, context = context, extension = "jpeg")
            val phone_intent = Intent(Intent.ACTION_CALL)
            phone_intent.data = Uri.parse("tel:$phone")

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
                IconButton(onClick = {
                    context.startActivity(Intent(context, chatActivity::class.java)
                        .putExtra("name", name)
                        .putExtra("phone", phone))
                }) {
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
                if (loadUserImg != null){
                    Image(
                        painter = rememberAsyncImagePainter(model = loadUserImg),
                        contentDescription = "",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(50))
                    )
                }else {
                    Image(
                        painter = userImg,
                        contentDescription = "",
                        modifier = Modifier.size(60.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = name,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp
                    )
                    Text(time, color = Color(0xFF919191))
                }
                Spacer(modifier = Modifier.weight(0.5f))
                IconButton(onClick = { /*TODO*/ }) {
                    Image(painter = phoneImg, contentDescription = "")
                }
                IconButton(onClick = {
                    context.startActivity(phone_intent)
                    viewModel.addToCallLog(name = name, phone = phone)
                }) {
                    Image(painter = cameraImg, contentDescription = "")
                }
            }
            Divider(thickness = 1.dp, modifier = Modifier.padding(start = 70.dp))
        }
    }
}
