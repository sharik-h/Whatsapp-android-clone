package com.example.whatsapp_clone.chatActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class attachmentActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageUri = intent.getStringExtra("imageUri")
        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        setContent {
            attachmentSelectionPage(imageUri, name, phone)
        }
    }
}