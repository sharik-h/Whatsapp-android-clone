package com.example.whatsapp_clone.chatActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.whatsapp_clone.newChatActivity.chatDetailsPage

class chatDetailActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val lstseen = intent.getStringExtra("lstseen")
        setContent {
            chatDetailsPage(name!!, phone!!, lstseen)
        }
    }
}