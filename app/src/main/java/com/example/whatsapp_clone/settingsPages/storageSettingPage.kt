package com.example.whatsapp_clone.settingsPages

import android.provider.Contacts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.R
import java.lang.ProcessBuilder.Redirect.to

@Preview(showBackground = true)
@Composable
fun storageSettingsPage() {
    val arrowImg = painterResource(id = R.drawable.arrow_back)
    val folderImg = painterResource(id = R.drawable.ic_baseline_folder_24)
    val storageImg = painterResource(id = R.drawable.storage_img)

    val list = listOf(
        "When using mobile data" to "Photos",
        "When Connected on Wi-Fi" to "All media",
        "When roaming" to "No media"
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        TopAppBar(backgroundColor = Color(0xFF008268)) {
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = arrowImg, contentDescription = "")
            }
            Text(text = "Storage and data", fontSize = 20.sp, color = Color.White)
        }
        storageOptions(
            Icon = folderImg,
            Name = "Manage storage",
            info = "317.0 MB",
            radio = false
        )
        Divider(thickness = 0.5.dp)
        storageOptions(
            Icon = storageImg,
            Name = "Network usage",
            info = "213.7 MB sent . 223.8 MB received",
            radio = false
        )
        storageOptions(
            Icon = null,
            Name = "Use less data for calls",
            info = null,
            radio = true
        )
        Divider(thickness = 0.5.dp)
        Text(
            text = "Media auto-download",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF818584),
            modifier = Modifier.padding(start = 20.dp, top = 20.dp)
        )
        Text(
            text = "Voice messages are always automatically downloaded",
            color = Color(0xFF818584),
            modifier = Modifier.padding(start = 20.dp, top = 8.dp)
        )
        list.forEach {
            storageOptions(Icon = null, Name = it.first, info = it.second, radio = false)
        }
        Divider(thickness = 0.5.dp)
        Text(
            text = "Media upload quality",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF818584),
            modifier = Modifier.padding(start = 20.dp, top = 20.dp)
        )
        Text(
            text = "Choose the quality of media files to be sent",
            color = Color(0xFF818584),
            modifier = Modifier.padding(start = 20.dp, top = 8.dp)
        )
        storageOptions(
            Icon = null,
            Name = "Photo upload quality",
            info = "Auto (recommended)",
            radio = false
        )
    }
}


@Composable
fun storageOptions(
    Icon: Painter?,
    Name: String,
    info: String?,
    radio: Boolean
) {
    var checked by remember{ mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(19.dp)
            .clickable { if (radio)  checked = !checked },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon?.let { Image(painter = Icon, contentDescription = "") }
        var width = 30
        if (Icon == null ) width = 55
        Spacer(modifier = Modifier.width(width.dp))
        Column(Modifier.weight(0.7f)) {
            androidx.compose.material3.Text(text = Name, fontSize = 15.sp)
            info?.let {
                androidx.compose.material3.Text(text = info, color = Color(0xFF818584))
            }

        }
        if (radio) Switch(checked = checked, onCheckedChange = {checked = !checked})
    }
}