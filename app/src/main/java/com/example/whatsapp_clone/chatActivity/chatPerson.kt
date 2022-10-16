package com.example.whatsapp_clone.chatActivity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.ByteArrayOutputStream
import coil.compose.rememberAsyncImagePainter
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.ui.theme.green100
import com.example.whatsapp_clone.ui.theme.green60
import com.example.whatsapp_clone.ui.theme.grey60
import com.example.whatsapp_clone.viewmodel.FirestoreViewModel
import java.time.LocalDate


@Composable
fun chatPerson( name: String, phone: String, msgdate: String?, viewModel: FirestoreViewModel) {

    viewModel.loadChat(phone = phone)
    viewModel.unseen(phone)

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
    val documentImg = painterResource(id = R.drawable.document_white)
    val cameraWhiteImg = painterResource(id = R.drawable.camera_img)
    val galleryImg = painterResource(id = R.drawable.gallery_image)
    val audioImg = painterResource(id = R.drawable.audio_white)
    val locationImg = painterResource(id = R.drawable.location_white)
    val contactImg = painterResource(id = R.drawable.person_white)
    var micOrSend = Pair(micImg, 30)
    val cdate = LocalDate.now().toString()
    var tempDate = msgdate
    var message by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var showAlert by remember { mutableStateOf(false) }
    val chats by viewModel.chats.observeAsState(initial = emptyList())
    val unSeen by viewModel.unSeen.observeAsState()
    val context = LocalContext.current
    val phone_intent = Intent(Intent.ACTION_CALL)
    phone_intent.data = Uri.parse("tel:$phone")
    var profileImg = viewModel.loadImageBitmap(context, phone, "jpeg")
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val glauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        context.startActivity(Intent(context, attachmentActivity::class.java)
            .putExtra("imageUri",imageUri.toString())
            .putExtra("name", name)
            .putExtra("phone", phone))
    }
    var bitmap by remember { mutableStateOf<Uri?>(null) }
    val clauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) {
        val bytes = ByteArrayOutputStream()
        it.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, it, "Title", null)
        bitmap = Uri.parse(path.toString())
        imageUri = null
        context.startActivity(Intent(context, attachmentActivity::class.java)
            .putExtra("imageUri",bitmap.toString())
            .putExtra("name", name)
            .putExtra("phone", phone))
    }
    val dlauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickContact() ) {
        showAlert = true
    }
    val doclauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) {
        showAlert = true
    }

    Column(Modifier.fillMaxSize()) {
                Image(painter = chatBgImg, contentDescription = "", modifier = Modifier.fillMaxSize())
    }


    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
//            .verticalScroll(rememberScrollState()),
    ) {

        TopAppBar(backgroundColor = green60) {
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

                if (profileImg == null) {
                    Image(
                        painter = userImg,
                        contentDescription = "",
                        modifier = Modifier.size(45.dp)
                    )
                }else {
                    Image(
                        painter = rememberAsyncImagePainter(model = profileImg),
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(50))
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
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
                modifier = Modifier
                    .padding(9.dp)
                    .clickable {
                        viewModel.addToCallLog(name, phone)
                        context.startActivity(phone_intent)
                    }
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


                    if(item.message  == ""){
                        var day = item.date
                        if (day == cdate) day = "Today"
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()) {
                            Box(modifier = Modifier
                                .clip(RoundedCornerShape(30))
                                .background(Color.White)
                                .padding(horizontal = 8.dp, vertical = 6.dp)) {
                                day?.let {
                                    Text(text = day, fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                                }
                            }
                        }
                    }else if (item.id == "2" && item.messageType == 1) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30))
                                    .background(Color.White)
                                    .padding(vertical = 7.dp, horizontal = 10.dp)
                            ) {
                                Text(
                                    text = item.time.toString().dropLast(7),
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(end = 5.dp),
                                    color = grey60
                                )
                                Text(
                                    text = item.message.toString(),
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }else if (item.id == "1" && item.messageType == 1) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(30))
                                    .background(green100)
                                    .padding(vertical = 7.dp, horizontal = 10.dp)
                            ) {
                                Text(
                                    text = item.message.toString(),
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = item.time.toString().dropLast(7),
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 5.dp),
                                    color = grey60
                                )
                            }
                        }
                    } else if (item.id == "1" && item.messageType == 2) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                          Box(
                              contentAlignment = Alignment.BottomEnd,
                              modifier = Modifier
                                  .heightIn(min = 40.dp, max = 360.dp)
                                  .widthIn(min = 40.dp, max = 280.dp)
                                  .clip(RoundedCornerShape(5))
                                  .background(green100)
                                  .padding(4.dp)) {
                              val bitmap = viewModel.loadImageBitmap(context,item.message!!,"jpeg")
                              Image(
                                  painter = rememberAsyncImagePainter(bitmap),
                                  contentDescription = "",
                                  modifier = Modifier.clip(RoundedCornerShape(5))
                              )
                              Text(
                                  text = item.time.toString().dropLast(7),
                                  fontSize = 12.sp,
                                  color = Color.White,
                                  modifier = Modifier.padding(end = 9.dp, bottom = 2.dp)
                              )
                          }
                        }
                    } else if (item.id == "2" && item.messageType == 2) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.BottomEnd,
                                modifier = Modifier
                                    .heightIn(min = 40.dp, max = 360.dp)
                                    .widthIn(min = 40.dp, max = 280.dp)
                                    .clip(RoundedCornerShape(5))
                                    .background(green100)
                                    .padding(4.dp)) {
                                val bitmap = viewModel.loadImageBitmap(context,item.message!!,"jpeg")
                                Image(
                                    painter = rememberAsyncImagePainter(bitmap),
                                    contentDescription = "",
                                    modifier = Modifier.clip(RoundedCornerShape(5))
                                )
                                Text(
                                    text = item.time.toString().dropLast(7),
                                    fontSize = 12.sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(end = 9.dp, bottom = 2.dp)
                                )
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
                    IconButton(onClick = {
                    expanded = true
                    }) {
                        Image(painter = attachFileImg, contentDescription = "")
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        Row(Modifier.padding(horizontal = 25.dp, vertical = 15.dp)) {
                            buttonSample(
                                icon = documentImg,
                                name = "Document",
                                color = 0xFF9060FF,
                                onClick = {
                                    doclauncher.launch(arrayOf("application/pdf", "application/doc"))
                                    expanded = false
                                } )
                            buttonSample(
                                icon = cameraWhiteImg,
                                name = "Camera",
                                color = 0xFFF35C7C,
                                onClick = {
                                    clauncher.launch()
                                    expanded = false
                                })
                            buttonSample(
                                icon = galleryImg,
                                name = "Gallery",
                                color = 0xFFA145DB,
                                onClick = {
                                    expanded = false
                                    glauncher.launch("*/*")
                                })
                        }
                        Row(Modifier.padding(horizontal = 25.dp, vertical = 5.dp)) {
                            buttonSample(
                                icon = audioImg,
                                name = "Audio",
                                color = 0xFFF07B55,
                                onClick = {
                                    expanded = false
                                    showAlert = true
                                })
                            buttonSample(
                                icon = locationImg,
                                name = "Location",
                                color = 0xFF3DB86F,
                                onClick = {
                                    expanded = false
                                    showAlert = true
                                })
                            buttonSample(
                                icon = contactImg,
                                name = "Contact",
                                color = 0xFF096097,
                                onClick = {
                                    expanded = false
                                    dlauncher.launch()
                                })
                        }
                    }
                }
                    if (message == "") {
                        IconButton(onClick = { clauncher.launch() }) {
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
                            if (tempDate != cdate) {
                                viewModel.sendMessage(phone,"", unSeen?.minus(1) ?: 0, 1)
                                tempDate = cdate
                            }
                            viewModel.sendMessage(phone,message, unSeen ?: 0,1)
                            message = ""
                        }
                    }) {
                        Image(
                            painter = micOrSend.first,
                            contentDescription = "",
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .padding(padd)
                                .size(micOrSend.second.dp)
                        )
                    }
                }

            }
        }
    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            confirmButton = {
                TextButton(onClick = { showAlert = false }) {
                    Text(text = "Ok", color = Color.Black)
                }
            },
            title = { Text(text = "Sorry 😢😢", fontWeight = FontWeight.SemiBold, fontSize = 22.sp) },
            text = { Text(text = "This option is not yet supported by this application.") }
        )
    }
}

@Composable
fun buttonSample(
    icon: Painter,
    name: String,
    color: Long,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .height(95.dp)
    ) {
        IconButton(
            onClick = onClick ,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .size(55.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(color))
        ) {
            Image(painter = icon, contentDescription = "")
        }
        Text(text = name)
    }
}