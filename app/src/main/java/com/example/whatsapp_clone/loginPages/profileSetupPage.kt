package com.example.whatsapp_clone.loginPages

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.example.whatsapp_clone.MainActivity
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.viewmodel.firestoreViewModel
import com.google.firebase.auth.FirebaseAuth


class profileSetupActivity: ComponentActivity() {

    val viewmodel: firestoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            profileSetupPage()
        }

    }

    @Composable
    fun profileSetupPage() {
        val userImg = painterResource(id = R.drawable.add_photo_grey)
        var name by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }
        var imageUri by remember { mutableStateOf<Uri?>(null) }
        var bitmap by remember { mutableStateOf<Bitmap?>(null) }
        val glauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                imageUri = uri
                bitmap = null
            }
        val clauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                bitmap = it
                imageUri = null
            }
        val context = LocalContext.current

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Profile info",
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 23.sp,
                color = Color(0xFF00B88D)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Please provide your name and optional photo.",
                textAlign = TextAlign.Center,
                color = Color(0xFF858585)
            )
            Spacer(modifier = Modifier.height(25.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(130.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFE2E2E2))
                    .clickable { expanded = true }
            ) {

                if (imageUri != null) {

                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "",
                        Modifier.fillMaxSize()
                    )
                } else if (bitmap != null) {

                    Image(
                        painter = rememberAsyncImagePainter(bitmap),
                        contentDescription = "",
                        Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        painter = userImg,
                        contentDescription = "",
                        modifier = Modifier.size(50.dp)
                    )
                }

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
            TextField(
                value = name,
                onValueChange = { name = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF008268),
                    unfocusedIndicatorColor = Color(0xFF008268),
                    cursorColor = Color(0xFF008268),
                    textColor = Color(0xFF008268)
                ),
            )

            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    val image = if (imageUri != null) imageUri.toString() else bitmap.toString()
                    val currentuser = FirebaseAuth.getInstance().currentUser
                    if (currentuser?.uid != null) {
                        viewmodel.addNewUser(
                            uid = currentuser.uid,
                            uri = image,
                            Name = name,
                            Phone = currentuser.phoneNumber ?: "0"
                        )
                    }
                    viewmodel.saveImage(context,image,"jpeg")
                    finishAffinity()
                    context.startActivity(Intent(context, MainActivity::class.java))
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF008268))
            ) {
                Text(text = "Next", color = Color.White)
            }
        }

    }


}
