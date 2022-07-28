package com.example.whatsapp_clone.viewmodel


import android.util.Log
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.example.whatsapp_clone.data.detailFormat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class firestoreViewModel: ViewModel() {

    val database = Firebase.firestore

    fun addNewUser(
        uid: String,
        Name: String,
        Phone: Long
    ) {
        val details = detailFormat(uid,Name,Phone)
        database
            .collection("whatsappclone/users/${uid}")
            .add(details)
    }


}