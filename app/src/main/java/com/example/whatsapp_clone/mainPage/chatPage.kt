package com.example.whatsapp_clone.mainPage

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.modelItems.chatItemModel
import com.example.whatsapp_clone.ui.theme.green60
import com.example.whatsapp_clone.viewmodel.FirestoreViewModel


@Composable
fun chatPage(viewModel: FirestoreViewModel) {

    val context = LocalContext.current
    viewModel.getData(context)
    viewModel.getAllUsers()
    viewModel.myNotifications()
    val userDetails by viewModel.userDetails.observeAsState(initial = emptyList())
    val availableUsers by viewModel.allAvailableUsers.observeAsState(initial = emptyList())
    val myNotifications by viewModel.notSeen.observeAsState()
    val allusers = availableUsers.toTypedArray()
    var notification = 0

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {
        FloatingActionButton(
            onClick = { context.startActivity(Intent(context, newChatActivity::class.java).putExtra("availableusers", allusers)) },
            modifier = Modifier.size(57.dp),
            shape = RoundedCornerShape(50),
            containerColor = green60
            ) {
            val chatimg = painterResource(id = R.drawable.chat_img_whilte)
            Image(painter = chatimg, contentDescription = "")
        }
    }
    
    Column(Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)) {
            items(items = userDetails) {
               for (i in myNotifications!!) {
                   if (i.first == it.phone) notification = i.second
               }
                chatItemModel(it ,notification)
            }
        }
    }

}