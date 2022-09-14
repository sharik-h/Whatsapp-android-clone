package com.example.whatsapp_clone.chatActivity

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.whatsapp_clone.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

@Composable
fun attachmentSelectionPage(imageUri: String?, name: String?, phone: String?) {
    val closeImg = painterResource(id = R.drawable.close_white)
    val cropImg = painterResource(id = R.drawable.crop_rotate_white)
    val smileImg = painterResource(id = R.drawable.imogi_white)
    val editImg = painterResource(id = R.drawable.edit_white)
    val galleryImg = painterResource(id = R.drawable.photo_library_grey)
    val disappearImg = painterResource(id = R.drawable.dissapear_grey)
    val sendImg = painterResource(id = R.drawable.send_white)
    val arrowImg =  painterResource(id = R.drawable.arrow_forward_white)
    var message by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(painter = rememberAsyncImagePainter(imageUri), contentDescription = "", modifier = Modifier.fillMaxSize())
    }

    Column(
        modifier = Modifier.fillMaxSize()) {
        TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = closeImg, contentDescription = "", modifier = Modifier.size(30.dp))
            }
            Spacer(modifier = Modifier.weight(0.5f))
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = cropImg, contentDescription = "", modifier = Modifier.size(30.dp))
            }
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = smileImg, contentDescription = "", modifier = Modifier.size(30.dp))
            }
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = editImg, contentDescription = "", modifier = Modifier.size(30.dp))
            }
        }
        
        Spacer(modifier = Modifier.weight(0.8f))
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
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Image(painter = galleryImg, contentDescription = "", Modifier.size(25.dp))
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
                IconButton(onClick = { }) {
                    Image(painter = disappearImg, contentDescription = "")
                }
            }
            Spacer(modifier =Modifier.width(4.dp))
            IconButton(
                onClick = { /*TODO*/ },
                Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF00A584))
                    .size(45.dp)
            ) {
                Image(painter = sendImg, contentDescription = "", modifier = Modifier
                    .padding(start = 3.dp)
                    .size(20.dp))
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Image(painter = arrowImg, contentDescription = "" , modifier = Modifier.size(20.dp))
            Text(text = name!!, color = Color.White, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}