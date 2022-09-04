package com.example.whatsapp_clone.viewmodel


import android.util.Log
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whatsapp_clone.data.detailFormat
import com.example.whatsapp_clone.data.messageFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.LocalTime

class firestoreViewModel: ViewModel() {

    val database = Firebase.firestore
    val currentUser = FirebaseAuth.getInstance().uid
    val myPhone = FirebaseAuth.getInstance().currentUser?.phoneNumber
    val userDetails: MutableLiveData<List<detailFormat>> = MutableLiveData<List<detailFormat>>()
    var allAvailableUsers : MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    val currentTime = LocalTime.now().toString()
    val currentDate = LocalDate.now().toString()

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

    fun sendMessage(uid:String, phone: String, message: String) {
        val msg1 = messageFormat(1,message,currentTime,currentDate)
        database.collection("chats/$currentUser/$phone")
           .add(msg1)
        val msg2 = messageFormat(2, message, currentTime, currentDate)
        database.collection("chats/$uid/$myPhone")
            .add(msg2)
        database.document("whatsappclone/chats/$currentUser/$phone")
            .update(mapOf("lastmsg" to message, "msgdate" to currentDate))
    }

}