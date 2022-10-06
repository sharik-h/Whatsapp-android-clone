package com.example.whatsapp_clone.settingsPages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.viewmodel.firestoreViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun profileSettingsPage(viewModel: firestoreViewModel) {
    val backArrowImg = painterResource(id = R.drawable.arrow_back)
    val roundedUserImg = painterResource(id = R.drawable.circle_img)
    val userImg = painterResource(id = R.drawable.person_img)
    val infoImg = painterResource(id = R.drawable.about_img)
    val callImg = painterResource(id = R.drawable.call_img)
    val circleImg = painterResource(id = R.drawable.circlebg_img)
    val cameraImg = painterResource(id = R.drawable.camera_img)
    val editImg = painterResource(id = R.drawable.edit_img)
    val context = LocalContext.current
    val number = FirebaseAuth.getInstance().currentUser?.phoneNumber
    viewModel.getMyName()
    val myName by viewModel.myName.observeAsState()
    val profilePic = viewModel.loadImageBitmap(context,number!!,"jpeg")
    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(elevation = 0.dp, backgroundColor = Color(0xFF008268)) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = backArrowImg, contentDescription = "")
            }
            Text(text = "Profile", color = Color.White, fontSize = 20.sp)
        }


        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 15.dp), horizontalArrangement = Arrangement.Center ) {
            IconButton(onClick = { /*TODO*/ }){
                Box {
                    if (profilePic == null) {
                        Image(
                            painter = roundedUserImg,
                            contentDescription = "",
                            modifier = Modifier.size(190.dp)
                        )
                    }else {
                        Image(
                            painter = rememberAsyncImagePainter(profilePic),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(10.dp)
                                .clip(RoundedCornerShape(50))
                                .size(160.dp)
                        )
                    }
                    Image(painter = circleImg, contentDescription = "", modifier = Modifier
                        .padding(start = 125.dp, top = 128.dp)
                        .size(50.dp))
                    Image(painter = cameraImg, contentDescription = "", modifier = Modifier
                        .padding(start = 139.dp, top = 142.dp)
                        .size(22.dp))
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(horizontal = 20.dp)
        ) {
            Image(
                painter = userImg,
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 5.dp)
                    .size(25.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Box {
                Column(Modifier.fillMaxWidth()) {
                    Text(text = "Name",  color = Color(0xFF818584))
                    Text(text = myName ?: "",
                        fontSize = 15.sp,)
                    Text(
                        text = "This is not your username or pin, This name will be visible to your Whatsapp contacts.",
                        color = Color(0xFF818584)
                    )
                }
                Image(
                    painter = editImg,
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(),
                    alignment = Alignment.TopEnd
                )
            }
        }
        Divider(thickness = 0.5.dp, modifier = Modifier.padding(start = 73.dp, top = 10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = infoImg,
                contentDescription = "",
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Box() {
                Column(Modifier.fillMaxWidth()) {
                    Text(text = "About")
                    Text(
                        text = "Hey there! I am using Whatsapp.",
                        fontSize = 15.sp
                    )
                }
                Image(
                    painter = editImg,
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(),
                    alignment = Alignment.TopEnd
                )
            }
        }
        Divider(thickness = 0.5.dp, modifier = Modifier.padding(start = 73.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = callImg,
                contentDescription = "",
                modifier = Modifier.size(25.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Column(Modifier.fillMaxWidth()) {
                Text(text = "Phone")
                Text(
                    text = number!!,
                    fontSize = 15.sp
                )
            }
        }
    }
}