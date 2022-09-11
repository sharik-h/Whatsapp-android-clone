package com.example.whatsapp_clone.modelItems

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.viewmodel.firestoreViewModel


@Composable
fun callLogModel(
    Name: String,
    time: String,
    type: String,
    viewModel: firestoreViewModel,
    phone: String?
) {
    val userImg = painterResource(id = R.drawable.circle_img)
    val phoneImg = painterResource(id = R.drawable.phone_green)
    val cameraImg = painterResource(id = R.drawable.video_camera)
    val callRecived = painterResource(id = R.drawable.call_received)
    val callMade = painterResource(id = R.drawable.call_made)
    val outgoingMissed = painterResource(id = R.drawable.outgoing_missed)
    val incomingMissed = painterResource(id = R.drawable.incoming_missed)
    val context = LocalContext.current
    val phone_intent = Intent(Intent.ACTION_CALL)
    phone_intent.data = Uri.parse("tel:$phone")


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(onClick = { /*TODO*/ }) {
            Image(painter = userImg, contentDescription = "", modifier = Modifier.size(55.dp))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(0.75f)) {
            Text(text = Name, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
            Row {
                if (type == "1") Image(painter = callRecived, contentDescription = "", modifier = Modifier.size(19.dp))
                else if (type == "2")  Image(painter = callMade, contentDescription = "", modifier = Modifier.size(19.dp))
                else if (type == "3")  Image(painter = incomingMissed, contentDescription = "", modifier = Modifier.size(19.dp))
                Text(text = time.dropLast(7))
            }
        }

        IconButton(onClick = {
            context.startActivity(phone_intent)
            viewModel.addToCallLog(name = Name, phone = phone!!)

        }) {
            Image(painter = phoneImg, contentDescription ="")
        }
    }
}