package com.example.whatsapp_clone.satusActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.viewmodel.firestoreViewModel
import java.util.*
import kotlin.concurrent.timerTask

class viewStatusActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra("name")
        val time = intent.getStringExtra("time")
        val statusNames = intent.getStringArrayExtra("statusNames")
        val viewModel: firestoreViewModel by viewModels()
        setContent {
            statusViewPage(name = name!!, time = time!!, viewModel, statusNames)
        }
    }

    @Composable
    private fun statusViewPage(
        name: String,
        time: String,
        viewModel: firestoreViewModel,
        statusNames: Array<String>?
    ) {
        val backArrowImg = painterResource(id = R.drawable.arrow_back)
        val optionImg = painterResource(id = R.drawable.option_img)
        val userImg = painterResource(id = R.drawable.circle_img)
        val context = LocalContext.current
        val Images = viewModel.loadStatusImages(context, statusNames)
        var image by remember { mutableStateOf(Images[0]) }
        var size = statusNames!!.size
        var progress by remember { mutableStateOf(0.0) }
        val finishTime: Int = 5000 * size
        Timer().schedule(timerTask{
            finish()
        },finishTime.toLong())

        Timer().schedule(timerTask {
            progress += 0.0195
        }, 26,5000)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Images.forEach {
                Timer().schedule(timerTask {
                    image = it
                }, 5000)

            }
            Image(painter = rememberAsyncImagePainter(image), contentDescription = "")
        }

        Column(modifier = Modifier.fillMaxSize()) {
            LinearProgressIndicator(progress = progress.toFloat(), color = Color.White)
            TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            ) {
                Image(painter = backArrowImg, contentDescription = "")
                Spacer(modifier = Modifier.width(10.dp))
                Image(painter = userImg, contentDescription = "", modifier = Modifier.size(50.dp))
                Spacer(modifier = Modifier.width(10.dp))
               Column(verticalArrangement = Arrangement.Center) {
                   Text(text = name, fontSize = 18.sp, color = Color.White)
                   Text(text = time, color = Color.White)
               }
                Spacer(modifier = Modifier.weight(0.7f))
                Image(painter = optionImg, contentDescription = "")
            }
        }

    }
}