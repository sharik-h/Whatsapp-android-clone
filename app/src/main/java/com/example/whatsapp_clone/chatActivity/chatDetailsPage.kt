package com.example.whatsapp_clone.newChatActivity

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.ui.theme.green80
import com.example.whatsapp_clone.ui.theme.grey60
import com.example.whatsapp_clone.ui.theme.grey80
import com.example.whatsapp_clone.viewmodel.FirestoreViewModel

@Composable
fun chatDetailsPage(name: String, phone: String, lstseen: String?, viewModel: FirestoreViewModel) {
    val backImg = painterResource(id = R.drawable.arrow_back_grey)
    val optionImg = painterResource(id = R.drawable.option_grey)
    val userImg = painterResource(id = R.drawable.circle_img)
    val phoneImg = painterResource(id = R.drawable.phone_green)
    val cameraImg = painterResource(id = R.drawable.video_camera)
    val searchImg = painterResource(id = R.drawable.search_green)
    val rupeeImg = painterResource(id = R.drawable.circlebg_img)
    val notificationImg = painterResource(id = R.drawable.notification_img)
    val musicImg = painterResource(id = R.drawable.music_grey)
    val galleryImg = painterResource(id = R.drawable.image_grey)
    val lockImg = painterResource(id = R.drawable.lock_img)
    val disappearingImg = painterResource(id = R.drawable.storage_img)
    val blockImg = painterResource(id = R.drawable.block_red)
    val reportImg = painterResource(id = R.drawable.thumb_down_red)
    val context = LocalContext.current
    var profileImg = viewModel.loadImageBitmap(context, phone, "jpeg")
    var isDropDown by remember{ mutableStateOf(false)}

    Box(Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(grey80)
                .verticalScroll(rememberScrollState())
        ) {


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(335.dp)
                    .background(Color.White)
            ) {

                Spacer(modifier = Modifier.height(15.dp))
                if (profileImg != null) {
                    Image(
                        painter = rememberAsyncImagePainter(profileImg),
                        contentDescription = "",
                        modifier = Modifier
                            .size(135.dp)
                            .clip(RoundedCornerShape(50)))
                }else {
                    Image(
                        painter = userImg,
                        contentDescription = "",
                        modifier = Modifier.size(135.dp))
                }
                Text(text = name, fontSize = 25.sp)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = phone, color = grey60, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(5.dp))
                lstseen?.let{
                    Box(
                        modifier = Modifier
                            .background(color = grey80)
                            .height(24.dp)
                            .padding(top = 3.dp)
                    ) {
                        Text(text = " Last seen $lstseen ", color = grey60)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    basicOptionModel(image = phoneImg, name = "Audio")
                    Spacer(modifier = Modifier.width(38.dp))
                    basicOptionModel(image = cameraImg, name = "Video")
                    Spacer(modifier = Modifier.width(38.dp))
                    basicOptionModel(image = searchImg, name = "Search")
                    Spacer(modifier = Modifier.width(38.dp))
                    basicOptionModel(image = rupeeImg, name = "Pay")
                }

            }
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(165.dp)
                    .background(Color.White)
            ) {
                ItemSample(
                    painterIcon = notificationImg,
                    optionTitle = "Mute notificaion",
                    title = "Mute notifications for...",
                    optionList = listOf("8 hours", "1 week", "Always"),
                    confirmMsg = "OK",
                    dismissMsg = "CANCEL"
                )
                ItemSample(
                    painterIcon = musicImg,
                    optionTitle = "Custom notification",
                    title = "Custom notifications",
                    msg = "This feature is not yet available to users.",
                    confirmMsg = "OK",
                    dismissMsg = "CLOSE"
                )
                ItemSample(
                    painterIcon = galleryImg,
                    optionTitle = "Media visibility",
                    title = "Show newly downloaded media from this chat in your phone's gallery?",
                    optionList = listOf("Default (Yes)", "Yes", "No"),
                    confirmMsg = "OK",
                    dismissMsg = "CANCEL"
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.White)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                ItemSample(
                    painterIcon = lockImg,
                    optionTitle = "Encryption",
                    optionDiscription = "Messages and calls are end-to-end \nencrypted. Tap to verify",
                    title = "Encryption",
                    msg = "This feature is not yet available to users.",
                    confirmMsg = "OK",
                    dismissMsg = "CLOSE"
                )
                ItemSample(
                    painterIcon = disappearingImg,
                    optionTitle = "Disappearing messages",
                    title = "Disappearing messages",
                    msg = "This feature is not yet available to users.",
                    confirmMsg = "OK",
                    dismissMsg = "CLOSE"
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(Color.White)
            ) {
                ItemSample(
                    painterIcon = blockImg,
                    optionTitle = "Block $name",
                    isRed = true,
                    title = "Block $name?",
                    confirmMsg = "BLOCK",
                    dismissMsg = "CANCEL",
                )
                ItemSample(
                    painterIcon = reportImg,
                    optionTitle = "Report $name",
                    isRed = true,
                    title = "Report $name",
                    confirmMsg = "REPORT",
                    dismissMsg = "CANCEL",
                    msg =  "The last five messages from this contact " +
                            "will be forwarded to whatsapp.This contact will not be notified",
                )
            }
        }
    }
    TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
        IconButton(onClick = { /*TODO*/ }) {
            Image(painter = backImg, contentDescription = "")
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { isDropDown = true }) {
            DropdownMenu(
                expanded = isDropDown,
                onDismissRequest = { isDropDown = false},
                modifier = Modifier.width(195.dp).padding(top = 10.dp, start = 15.dp)
            ) {
                dropDownOptionModel(Name = "Share")
                dropDownOptionModel(Name = "Edit")
                dropDownOptionModel(Name = "View in address book")
                dropDownOptionModel(Name = "Verify security code")
            }
            Image(painter = optionImg, contentDescription = "")
        }
    }
}

@Composable
fun basicOptionModel(image: Painter, name: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = name,
            color = green80,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun dropDownOptionModel(Name: String){
    Text(
        text = Name,
        color = Color.Black,
        fontSize = 17.sp,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 65.dp, height = 42.dp)
    )
}

@Composable
fun ItemSample(
    painterIcon: Painter,
    optionTitle: String,
    optionDiscription: String = "",
    isRed: Boolean = false,
    title: String? = null,
    confirmMsg: String? = null,
    dismissMsg: String? = null,
    msg: String? = null,
    optionList: List<String>? = null
) {
    var showAlert by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clickable { showAlert = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        Image(
            painter = painterIcon,
            contentDescription = "",
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(30.dp))
        Column(Modifier.fillMaxWidth()) {
            if (isRed) Text(text = optionTitle, fontSize = 15.sp, color = Color.Red)
            else Text(text = optionTitle, fontSize = 15.sp)
            if (optionDiscription != "") {
                Text(text = optionDiscription, color = grey60)
            }
        }
    }
    if (showAlert) {
        title?.let {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                confirmButton = {
                    TextButton(onClick = { showAlert = false }) {
                        Text(text = confirmMsg!!, color = Color.Black)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAlert = false }) {
                        Text(text = dismissMsg!!, color = Color.Black)
                    }
                },
                title = { Text(text = it, fontWeight = FontWeight.SemiBold, fontSize = 22.sp) },
                text = {
                    if (msg != null) {
                        Text(text = msg)
                    }
                    optionList?.let {
                        var selected by remember{ mutableStateOf("")}
                            Column {
                                it.forEach {
                                    Row(verticalAlignment = CenterVertically) {
                                        RadioButton(
                                            selected = selected == it,
                                            onClick = { selected = it }
                                        )
                                        Text(text = it, fontSize = 18.sp)
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}