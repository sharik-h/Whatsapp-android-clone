package com.example.whatsapp_clone.satusActivity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.chatActivity.attachmentActivity
import com.example.whatsapp_clone.viewmodel.FirestoreViewModel

class myStatusDetailActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: FirestoreViewModel by viewModels()
        setContent {
            statusDetail(viewModel)
        }
    }

    @Composable
    fun statusDetail(viewModel: FirestoreViewModel) {
        val context = LocalContext.current
        viewModel.loadMyStatusName()
        viewModel.getViewDetails()

        val bArrowImg = painterResource(id = R.drawable.arrow_back)
        val cameraImg = painterResource(id = R.drawable.camera_img)
        val namess by viewModel.loadMyStatusName.observeAsState(initial = emptyList())
        val viewDetails by viewModel.statusViews.observeAsState()
        val myStatusImages = viewDetails?.let { viewModel.loadMyStatus(context = context, names = namess, viewDetails = it) }
        var imageUri by remember { mutableStateOf<Uri?>(null) }
        val glauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                imageUri = uri
                context.startActivity(Intent(context, attachmentActivity::class.java)
                        .putExtra("imageUri",imageUri.toString()))
            }

        Column(Modifier.fillMaxWidth()) {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                elevation = 0.dp,
                backgroundColor = Color(0xFF008F6D)
            ) {
                IconButton(onClick = { finish() }) {
                    Image(painter = bArrowImg, contentDescription = "")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "My status",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            myStatusImages?.let{
                LazyColumn() {
                    items(items = it) { image ->
                        StatusItem(
                            id = image.name,
                            image = image.image,
                            views = image.view,
                            time = image.time,
                            name = "My status",
                            viewModel = viewModel
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Your status updates will disappear after 24 hours.",
                Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color(0xFF4E4E4E)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            FloatingActionButton(
                modifier = Modifier.size(57.dp),
                shape = RoundedCornerShape(50),
                containerColor = Color(0xFF01AA88),
                onClick = { glauncher.launch("image/*") }) {
                Image(painter = cameraImg, contentDescription = "")
            }
        }
    }
}

@Composable
fun StatusItem(
    image: Bitmap,
    views: Int,
    time: String,
    name: String,
    viewModel: FirestoreViewModel,
    id: String
){
    val optionImg = painterResource(id = R.drawable.option_vertical_green)
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                context.startActivity(
                    Intent(context, viewStatusActivity::class.java)
                        .putExtra("name", name)
                )
            }
    ) {
        Spacer(modifier = Modifier.width(15.dp))
        Box(Modifier.size(60.dp)) {
            Image(
                painter = rememberAsyncImagePainter(image),
                contentDescription = "",
                Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50))
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column() {
            Text(
                text = "$views views",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = time, color = Color(0xFF616161))
        }
        Spacer(modifier = Modifier.weight(0.5f))
        IconButton(onClick = { expanded = true }) {
            Image(painter = optionImg, contentDescription = "")
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.width(195.dp),
            ) {
                    Text(
                        text = "    Forward",
                        color = Color.Black,
                        fontSize = 17.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(width = 65.dp, height = 42.dp)
                            .clickable { expanded = false }
                    )
                    Text(
                        text = "    Share...",
                        color = Color.Black,
                        fontSize = 17.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(width = 65.dp, height = 42.dp)
                            .clickable { expanded = false }
                    )
                    Text(
                        text = "    Delete",
                        color = Color.Black,
                        fontSize = 17.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(width = 65.dp, height = 42.dp)
                            .clickable {
                                expanded = false; viewModel.deleteStatus(id.drop(13).dropLast(9), context)
                            }
                    )
            }
        }
    }
}