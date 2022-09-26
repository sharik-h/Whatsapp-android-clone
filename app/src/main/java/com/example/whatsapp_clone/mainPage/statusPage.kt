package com.example.whatsapp_clone.mainPage

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
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
import coil.compose.rememberAsyncImagePainter
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.chatActivity.attachmentActivity
import com.example.whatsapp_clone.satusActivity.myStatusDetailActivity
import com.example.whatsapp_clone.satusActivity.viewStatusActivity
import com.example.whatsapp_clone.viewmodel.firestoreViewModel

@Composable
fun statusPage(viewModel: firestoreViewModel) {
    Column(Modifier.fillMaxSize()) {
        val userImg = painterResource(id = R.drawable.circle_img)
        val addImg = painterResource(id = R.drawable.add_circle)
        val optionImg = painterResource(id = R.drawable.option_horixzontal_green)
        val context = LocalContext.current
        viewModel.loadMyStatus(context)
        var imageUri by remember { mutableStateOf<Uri?>(null) }
        var bitmap by remember { mutableStateOf<Uri?>(null) }
        val glauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                imageUri = uri
                bitmap = null
                context.startActivity(Intent(context, attachmentActivity::class.java)
                    .putExtra("imageUri",imageUri.toString()))
            }
        var myStatus = mutableListOf<Bitmap>()
        myStatus = viewModel.loadMyStatus(context)
        viewModel.allStatusUsers()
        val allStatusUser by viewModel.allStatusUsers.observeAsState(initial = emptyMap())
        viewModel.getAllStatusNames(allStatusUser)
        val allStatusList by viewModel.allStatusList.observeAsState(initial = emptyList())
        viewModel.loadAllStatus(allStatusList = allStatusList, context = context)
        val allStatus by viewModel.allStatus.observeAsState(initial = emptyList())

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clickable {
                    if (myStatus.isEmpty()){
                        glauncher.launch("image/*")
                    }else{
                        context.startActivity(Intent(context, viewStatusActivity::class.java)
                            .putExtra("name","My Status"))
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(15.dp))
            if (myStatus.isEmpty()){
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
                            .clip(RoundedCornerShape(50))
                            .background(Color.White)
                    )
                }
            }else{
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(50))) {
                    Image(painter = rememberAsyncImagePainter(myStatus.last()), contentDescription = "")
                }
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
            Spacer(modifier = Modifier.weight(0.5f))
            if (!myStatus.isEmpty()) {
                IconButton(onClick = {
                    context.startActivity(Intent(context, myStatusDetailActivity::class.java))
                }) {
                    Image(painter = optionImg, contentDescription = "")
                }
            }
        }
        var uvFirst = 0
        var vFirst = 0
        LazyColumn(){
            val items = allStatus.sortedBy { it.viewed }.reversed()
            items(items = items) { eachStatus->
                eachStatus.name?.let { name->
                    var statusNames = mutableListOf<String>()
                    allStatusList.forEach { eachPerson ->
                        if (eachPerson.name!!.dropLast(4) == name) {
                            statusNames = eachPerson.allNames!!
                        }
                    }
                    if (items.first().viewed == true && uvFirst == 0) {
                        uvFirst++
                        Text(
                            text = "Recent updates",
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 15.dp),
                            color = Color(0xFF616161)
                        )
                    }
                    if (eachStatus.viewed == false && vFirst == 0) {
                        if (uvFirst == 1){
                            Spacer(modifier = Modifier.height(25.dp))
                        }
                        vFirst++
                        Text(text = "Viewed updates",
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 15.dp),
                            color = Color(0xFF616161)
                        )
                    }
                   statusModel(
                       name = name,
                       time = eachStatus.time,
                       status = eachStatus.status,
                       statusNames = statusNames
                   )
                }
            }
        }
    }
}

@Composable
fun statusModel(
    name: String,
    time: String,
    status: MutableList<Bitmap>?,
    statusNames: MutableList<String>
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                context.startActivity(Intent(context, viewStatusActivity::class.java)
                        .putExtra("name", name)
                        .putExtra("time", time)
                        .putExtra("statusNames",statusNames.toTypedArray())
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(15.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(50))) {
            if (status != null){
                Image(painter = rememberAsyncImagePainter(status[0]), contentDescription = "")
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(text = time, color = Color(0xFF616161))
        }
    }
}