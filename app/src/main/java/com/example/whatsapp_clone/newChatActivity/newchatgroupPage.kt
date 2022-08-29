package com.example.whatsapp_clone.mainPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.R




class newChatActivity(): ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            newChatGroupPage()
        }
    }
}

@SuppressLint("Range")
@Composable
fun newChatGroupPage() {
    val arrowImg = painterResource(id = R.drawable.arrow_back)
    val circleImg = painterResource(id = R.drawable.circlebg_img)
    val optionsImg = painterResource(id = R.drawable.option_img)
    val searchImg = painterResource(id = R.drawable.search_img)
    val peopleimg = painterResource(id = R.drawable.people_img_white)
    val personimg = painterResource(id = R.drawable.add_person)
    val qrcode = painterResource(id = R.drawable.qrcode_grey)

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(backgroundColor = Color(0xFF008268)) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = arrowImg, contentDescription = "")
            }
            Text(text = "Select contact", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.weight(0.5f))
            Image(painter = searchImg, contentDescription = "", modifier = Modifier.padding(11.dp))
            Image(painter = optionsImg, contentDescription = "", modifier = Modifier.padding(11.dp))

        }
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(53.dp)
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box {
                    Image(
                        painter = circleImg,
                        contentDescription = "",
                        modifier = Modifier.size(50.dp)
                    )
                    Image(painter = peopleimg, contentDescription = "", modifier = Modifier.padding(12.dp))
                }
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "New group",
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(53.dp)
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box {
                    Image(
                        painter = circleImg,
                        contentDescription = "",
                        modifier = Modifier.size(50.dp)
                    )
                    Image(painter = personimg, contentDescription = "", modifier = Modifier.padding(12.dp))
                }
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "New contact",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.weight(0.5f))
                Image(painter = qrcode, contentDescription = "", modifier = Modifier.size(27.dp))
                Spacer(modifier = Modifier.weight(0.1f))
            }
            Text(
                text = "Contacts on WhatsApp",
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF818584),
                modifier = Modifier.padding(12.dp),
            )

            val context = LocalContext.current
            val contentresolever = context.contentResolver
            val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val cursor = contentresolever.query(uri, null, null, null, null,null)
            if (cursor != null) {
                if (cursor.count > 0) {
                    while (cursor.moveToNext()) {
                        newMembers(
                            Icon = null,
                            Name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                            info = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun newMembers(
    Icon: Painter?,
    Name: String,
    info: String,
) {
    val noUserIcons = painterResource(id = R.drawable.circle_img)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .clickable {},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        if (Icon != null) { Image(painter = Icon, contentDescription = "") }
        else {  Image(painter = noUserIcons, contentDescription = "", modifier = Modifier.size(48.dp))  }
        Spacer(modifier = Modifier.width(10.dp))
        Column(Modifier.weight(0.7f)) {
            Text(text = Name, fontSize = 16.sp)
            Text(text = info, color = Color(0xFF818584))
        }
    }
}