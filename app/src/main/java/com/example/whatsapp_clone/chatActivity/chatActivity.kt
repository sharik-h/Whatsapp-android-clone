package com.example.whatsapp_clone.chatActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.whatsapp_clone.viewmodel.firestoreViewModel

class chatActivity:ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uid = intent.getStringExtra("uid")
        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val msgdate = intent.getStringExtra("msgdate")
        val viewModel: firestoreViewModel by viewModels()
        setContent {
            chatPerson(uid!!,name!!, phone!!, msgdate, viewModel)
        }
    }
}