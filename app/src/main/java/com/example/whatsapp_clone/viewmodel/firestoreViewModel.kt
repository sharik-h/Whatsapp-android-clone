package com.example.whatsapp_clone.viewmodel


import android.util.Log
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whatsapp_clone.data.detailFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class firestoreViewModel: ViewModel() {

    val database = Firebase.firestore
    val currentUser = FirebaseAuth.getInstance().uid
    val userDetails: MutableLiveData<List<detailFormat>> = MutableLiveData<List<detailFormat>>()


    fun addNewUser(
        uid: String,
        Name: String,
        Phone: String
    ) {
        val details = detailFormat(uid,Name,Phone)
        database
            .collection("whatsappclone/users/${uid}")
            .add(details)
    }

    fun getData(): MutableList<detailFormat> {
        val activeChats : MutableList<detailFormat> = mutableListOf()
        database
            .collection("whatsappclone/chats/$currentUser")
            .addSnapshotListener {
                    querySnapshot, firebaseFirestoreException ->
                querySnapshot?.let { querySnapshot ->
                    var documents = querySnapshot.documents
                    var chats = ArrayList<detailFormat>()
                    documents.forEach{
                        var allchats = it.toObject(detailFormat::class.java)
                        allchats?.let {
                            chats.add(allchats)
                        }
                    }
                    userDetails.value = chats
                }
            }
        return activeChats
    }


}