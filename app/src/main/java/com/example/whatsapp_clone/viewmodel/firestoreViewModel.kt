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
    var allAvailableUsers : MutableLiveData<List<String>> = MutableLiveData<List<String>>()

    fun addNewUser(
        uid: String,
        Name: String,
        Phone: String
    ) {
        val details = detailFormat(uid,Name,Phone)
        database
            .document("users/$Phone")
            .set(details)
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

    fun getAllUsers() {
        var allUsers: MutableList<String> = mutableListOf()
         database.collection("users")
             .addSnapshotListener { querySnapshot, error ->
                 querySnapshot?.let {
                     querySnapshot.documents.forEach { doc->
                         allUsers.add(doc.id)
                     }
                     allAvailableUsers.value = allUsers
                 }
             }
    }

    fun addNewChat(name:String, number: String) {
        database
            .document("whatsappclone/chats/$currentUser/$number")
            .set(detailFormat(name = name, phone = number))
    }

    fun isUserPresent(name:String ,number: String) {
       database
           .document("whatsappclone/chats/$currentUser/$number")
           .get()
           .addOnSuccessListener {
              if (!it.exists()){
                  addNewChat(name,number)
              }
           }

    }

}