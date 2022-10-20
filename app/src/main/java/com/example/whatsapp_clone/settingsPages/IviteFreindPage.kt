package com.example.whatsapp_clone.settingsPages

import android.annotation.SuppressLint
import android.provider.ContactsContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.ui.theme.green60
import com.example.whatsapp_clone.ui.theme.grey60

@SuppressLint("Range")
@Composable
fun inviteFriendPage() {
    val arrowImg = painterResource(id = R.drawable.arrow_back)
    val circleImg = painterResource(id = R.drawable.circlebg_img)
    val shareImg = painterResource(id = R.drawable.ic_baseline_share_24)
    
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(backgroundColor = green60) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = arrowImg, contentDescription = "")
            }
            Text(text = "Invite a friend", fontSize = 20.sp, color = Color.White)
        }
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(61.dp)
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
                    Image(painter = shareImg, contentDescription = "", modifier = Modifier.padding(11.dp))
                }
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "Share link",
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "From Contacts",
                fontWeight = FontWeight.Bold,
                color = green60,
                modifier = Modifier.padding(12.dp),
            )

            val context = LocalContext.current
//        val permissionRequest = ActivityCompat.checkSelfPermission(context,  android.Manifest.permission.READ_CONTACTS)
//        val permissionGranted = PackageManager.PERMISSION_GRANTED
//        if ( permissionRequest != permissionGranted) {
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS), 101 )
//        }
            val contentresolever = context.contentResolver
            val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val cursor = contentresolever.query(uri, null, null, null, null,null)
            if (cursor != null) {
                if (cursor.count > 0) {
                    while (cursor.moveToNext()) {
                        inviteableMembers(
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
fun inviteableMembers(
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
            Text(text = info, color = grey60)
        }
    }
}