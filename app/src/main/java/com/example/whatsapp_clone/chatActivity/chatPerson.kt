package com.example.whatsapp_clone.chatActivity

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.viewmodel.firestoreViewModel


@Composable
fun chatPerson( name: String, phone: String, msgdate: String?, viewModel: firestoreViewModel) {

    viewModel.loadChat(phone = phone)

    val arrowImg = painterResource(id = R.drawable.arrow_back)
    val userImg = painterResource(id = R.drawable.circle_img)
    val phoneImg = painterResource(id = R.drawable.phone_white)
    val optionsImg = painterResource(id = R.drawable.option_img)
    val videoCamImg = painterResource(id = R.drawable.video_camera_white)
    val tagFaceImg = painterResource(id = R.drawable.tag_faces_grey)
    val cameraImg = painterResource(id = R.drawable.camera_img_grey)
    val attachFileImg = painterResource(id = R.drawable.attach_file)
    val bCircle = painterResource(id = R.drawable.circlebg_img)
    val micImg = painterResource(id = R.drawable.mic_img_white)
    val chatBgImg = painterResource(id = R.drawable.chat_background)
    val sendImg = painterResource(id = R.drawable.send_white)
    var micOrSend = Pair(micImg, 30)
    var message by remember { mutableStateOf("") }
    val chats by viewModel.chats.observeAsState(initial = emptyList())
    val context = LocalContext.current


    Column(Modifier.fillMaxSize()) {
                Image(painter = chatBgImg, contentDescription = "", modifier = Modifier.fillMaxSize())
    }

    Column(modifier = Modifier.fillMaxSize()) {
//        TopAppBar(backgroundColor = Color(0xFF008268)) {
//            Image(painter = arrowImg, contentDescription = "", Modifier.padding(5.dp))
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .weight(0.5f)
//                    .clickable {
//                        context.startActivity(
//                            Intent(
//                                context,
//                                chatDetailActivity::class.java
//                            )
//                                .putExtra("name", name)
//                                .putExtra("phone", phone)
//                                .putExtra("lstseen", msgdate)
//                        )
//                    }) {
//
//                Image(
//                    painter = userImg,
//                    contentDescription = "",
//                    modifier = Modifier.size(45.dp)
//                )
//                Text(
//                    text = name,
//                    fontSize = 20.sp,
//                    color = Color.White,
//                    fontWeight = FontWeight.SemiBold
//                )
////                Spacer(modifier = Modifier.weight(0.5f))
//
//            }
//            Image(
//                painter = videoCamImg,
//                contentDescription = "",
//                modifier = Modifier.padding(9.dp)
//            )
//            Image(
//                painter = phoneImg,
//                contentDescription = "",
//                modifier = Modifier.padding(9.dp)
//            )
//            Image(
//                painter = optionsImg,
//                contentDescription = "",
//                modifier = Modifier.padding(9.dp)
//            )
//        }
//        Column(
//            verticalArrangement = Arrangement.Bottom,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(bottom = 70.dp)) {
//            LazyColumn(modifier = Modifier.fillMaxWidth()){
//               items(items = chats) { item ->
//                   Spacer(modifier = Modifier.height(2.dp))
//                   if (item.id == "1") {
//                       Row(
//                           horizontalArrangement = Arrangement.Start,
//                           modifier = Modifier
//                               .fillMaxWidth()
//                               .padding(horizontal = 15.dp)
//                       ) {
//                           Box(
//                               Modifier
//                                   .clip(RoundedCornerShape(30))
//                                   .background(Color.White)
//                                   .padding(10.dp)
//                              ) {
//                               Text(text = item.message.toString())
//                           }
//                       }
//                   }else {
//                       Row(
//                           horizontalArrangement = Arrangement.End,
//                           modifier = Modifier
//                               .fillMaxWidth()
//                               .padding(horizontal = 15.dp)
//                       ) {
//                           Box(
//                               Modifier
//                                   .clip(RoundedCornerShape(30))
//                                   .background(Color(0xFFDBFFD0))
//                                   .padding(10.dp)
//                           ) {
//                               Text(text = item.message.toString())
//                           }
//                       }
//                   }
//               }
//            }
//        }
    }


    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
//            .verticalScroll(rememberScrollState()),
    ) {

        TopAppBar(backgroundColor = Color(0xFF008268)) {
            Image(painter = arrowImg, contentDescription = "", Modifier.padding(5.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(0.5f)
                    .clickable {
                        context.startActivity(
                            Intent(
                                context,
                                chatDetailActivity::class.java
                            )
                                .putExtra("name", name)
                                .putExtra("phone", phone)
                                .putExtra("lstseen", msgdate)
                        )
                    }) {

                Image(
                    painter = userImg,
                    contentDescription = "",
                    modifier = Modifier.size(45.dp)
                )
                Text(
                    text = name,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
//                Spacer(modifier = Modifier.weight(0.5f))

            }
            Image(
                painter = videoCamImg,
                contentDescription = "",
                modifier = Modifier.padding(9.dp)
            )
            Image(
                painter = phoneImg,
                contentDescription = "",
                modifier = Modifier.padding(9.dp)
            )
            Image(
                painter = optionsImg,
                contentDescription = "",
                modifier = Modifier.padding(9.dp)
            )
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.weight(0.8f)
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()){
                items(items = chats) { item ->
                    Spacer(modifier = Modifier.height(2.dp))
                    if (item.id == "2") {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                            Box(
                                Modifier
                                    .clip(RoundedCornerShape(30))
                                    .background(Color.White)
                                    .padding(10.dp)
                            ) {
                                Text(text = item.message.toString())
                            }
                        }
                    }else {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                            Box(
                                Modifier
                                    .clip(RoundedCornerShape(30))
                                    .background(Color(0xFFDBFFD0))
                                    .padding(10.dp)
                            ) {
                                Text(text = item.message.toString())
                            }
                        }
                    }
                }
            }
        }



            BottomAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color.White)
                        .height(45.dp)
                        .weight(0.75f)
                )
                {

                    IconButton(onClick = { /*TODO*/ }) {
                        Image(painter = tagFaceImg, contentDescription = "", Modifier.size(25.dp))
                    }
                    TextField(
                        value = message,
                        onValueChange = { message = it },
                        placeholder = { Text(text = "Message") },
                        modifier = Modifier.weight(0.5f),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        )
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(painter = attachFileImg, contentDescription = "")
                    }
                    if (message == "") {
                        IconButton(onClick = { /*TODO*/ }) {
                            Image(painter = cameraImg, contentDescription = "")
                        }
                    }
                }

                var padd = 15.dp
                if (message != "")  {
                    micOrSend =  Pair(sendImg, 23)
                    padd = 19.dp
                }
                else {
                    micOrSend = Pair(micImg, 30)
                    padd = 15.dp
                }
                Box() {
                    Image(painter = bCircle, contentDescription = "", Modifier.size(60.dp))
                    IconButton(onClick = {
                        if (message != ""){
                            viewModel.sendMessage(phone,message)
                            message = ""
                        }
                    }) {
                        Image(
                            painter = micOrSend.first,
                            contentDescription = "",
                            alignment = Alignment.Center,
                            modifier = Modifier.padding(padd).size(micOrSend.second.dp)
                        )
                    }
                }

            }
        }
}