package com.example.whatsapp_clone.mainPage

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.example.whatsapp_clone.viewmodel.firestoreViewModel
import java.io.ByteArrayOutputStream

@Composable
fun statusPage(viewModel: firestoreViewModel) {
    Column(Modifier.fillMaxSize()) {
        val userImg = painterResource(id = R.drawable.circle_img)
        val addImg = painterResource(id = R.drawable.add_circle)
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clickable {
                    glauncher.launch("image/*")
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
        }
        Text(
            text = "Recent updates",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 15.dp),
            color = Color(0xFF616161)
        )
    }
}
