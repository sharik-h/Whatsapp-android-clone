package com.example.whatsapp_clone.settingsPages

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.viewmodel.firestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import java.io.ByteArrayOutputStream

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
    var expanded by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Uri?>(null) }
    var newName by remember { mutableStateOf(myName) }
    var showAlert by remember { mutableStateOf(false) }
    val glauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        bitmap = null
        viewModel.updateProfilePic(imageUri , context)
    }
    val clauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        val bytes = ByteArrayOutputStream()
        it.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, it, "Title", null)
        bitmap = Uri.parse(path.toString())
        imageUri = null
        viewModel.updateProfilePic(bitmap, context)
    }

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
                    IconButton(onClick = { expanded = true },){
                        Image(painter = circleImg, contentDescription = "", modifier = Modifier
                            .padding(start = 125.dp, top = 128.dp)
                            .size(50.dp))
                        Image(painter = cameraImg, contentDescription = "", modifier = Modifier
                            .padding(start = 125.dp, top = 130.dp)
                            .size(22.dp))
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.width(95.dp),
                        ) {
                            TextButton(
                                onClick = {
                                    clauncher.launch()
                                    expanded = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                Text(text = "Camera")
                            }
                            TextButton(
                                onClick = {
                                    glauncher.launch("image/*")
                                    expanded = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                Text(text = "Gallery")
                            }
                        }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .clickable { showAlert = true }
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
            if (showAlert){
            AlertDialog(
                onDismissRequest = { showAlert = false },
                confirmButton = {
                    TextButton(onClick = {
                        showAlert = false
                        viewModel.updateMyName(newName)
                    }) {
                        Text(text = "Ok", color = Color.Black)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAlert = false }) {
                        Text(text = "Cancel", color = Color.Black)
                    }
                },
                title = {
                    Text(
                        text = "Enter the name",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp
                    )
                },
                text = {
                    TextField(
                        value = newName ?: "",
                        onValueChange = { newName = it },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFF008268)),
                        textStyle = TextStyle(fontSize = 20.sp),
                    )
                }
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