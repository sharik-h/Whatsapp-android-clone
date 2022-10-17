package com.example.whatsapp_clone.chatActivity

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import coil.compose.rememberAsyncImagePainter
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.ui.theme.green60
import com.example.whatsapp_clone.viewmodel.FirestoreViewModel
import java.time.LocalDate
import java.time.LocalTime


class SelectUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: FirestoreViewModel by viewModels()
        val imageUri = intent.getStringExtra("imageUri")
        setContent {
            SelectUser(viewModel, imageUri)
        }
    }


    @Composable
    fun SelectUser(viewModel: FirestoreViewModel, imageUri: String?) {

        val context = LocalContext.current
        viewModel.getData(context)
        val UserDetails by viewModel.userDetails.observeAsState(initial = emptyList())
        val arrowImg = painterResource(id = R.drawable.arrow_back)

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(backgroundColor = green60) {
                Image(painter = arrowImg, contentDescription = "", Modifier.padding(5.dp))
                Text(
                    text = "Select contact",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )

            }
            LazyColumn() {
                items(items = UserDetails) {
                    newMembers(
                        Image = it.image,
                        Name = it.name!!,
                        phone = it.phone!!,
                        imageUri = imageUri,
                        viewModel
                    )
                }
            }
        }
    }

    @Composable
    fun newMembers(
        Image: Bitmap?,
        Name: String,
        phone: String,
        imageUri: String?,
        viewModel: FirestoreViewModel,
    ) {
        val noUserIcons = painterResource(id = R.drawable.circle_img)
        val context = LocalContext.current
        viewModel.unseen(phone)
        val unSeen by viewModel.unSeen.observeAsState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(61.dp)
                .clickable {
                    val currentDate = LocalDate.now()
                    val currentTime = LocalTime.now()
                    viewModel.sendMessage(
                        phone = phone,
                        message = "$currentDate$currentTime",
                        unSeen = unSeen?.minus(1) ?: 0,
                        messageType = 2
                    )
                    viewModel.sendImage(
                        image = imageUri!!,
                        name = "$currentDate$currentTime",
                        phone = phone,
                        context = context,
                        extension = "jpeg"
                    )
                    finish()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            if (Image != null) {
                Image(
                    painter = rememberAsyncImagePainter(Image),
                    contentDescription = "",
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .size(48.dp)
                )
            } else {
                Image(
                    painter = noUserIcons,
                    contentDescription = "",
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(Modifier.weight(0.7f)) {
                Text(text = Name, fontSize = 16.sp)
            }
        }
    }

}