package com.example.whatsapp_clone.chatActivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.R



@Composable
fun chatPerson(name: String) {
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
    var message by remember { mutableStateOf("") }
    
    
    
    
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(backgroundColor = Color(0xFF008268)) {
            Image(painter = arrowImg, contentDescription = "",Modifier.padding(5.dp) )
            Image(painter = userImg, contentDescription = "", modifier = Modifier.size(45.dp))
            Text(text = name, fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.weight(0.5f))
            Image(painter = videoCamImg, contentDescription = "", modifier = Modifier.padding(9.dp))
            Image(painter = phoneImg, contentDescription = "", modifier = Modifier.padding(9.dp))
            Image(painter = optionsImg, contentDescription = "", modifier = Modifier.padding(9.dp))
        }
        Image(painter = chatBgImg, contentDescription = "", modifier = Modifier.fillMaxSize())
    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize(),
    ) {
        BottomAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(Color.White)
                .height(45.dp)
                .weight(0.75f)
            )
            {

                IconButton(onClick = { /*TODO*/ }) {
                    Image(painter = tagFaceImg, contentDescription = "",Modifier.size(25.dp))
                }
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    placeholder = { Text(text = "Message")},
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
                IconButton(onClick = { /*TODO*/ }) {
                    Image(painter = cameraImg, contentDescription = "")
                }
            }

            Box() {
                Image(painter = bCircle , contentDescription = "" ,Modifier.size(60.dp))
                IconButton(onClick = { /*TODO*/ }) {
                    Image(painter = micImg, contentDescription = "",
                        Modifier
                            .padding(15.dp)
                            .size(30.dp))
                }
            }
            
        }
    }
}