package com.example.whatsapp_clone.viewmodel


import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whatsapp_clone.data.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.LocalTime
import kotlin.collections.ArrayList

class firestoreViewModel: ViewModel() {

    val database = Firebase.firestore
    val storageref = Firebase.storage.reference
    val currentUser = FirebaseAuth.getInstance().uid
    val myPhone = FirebaseAuth.getInstance().currentUser?.phoneNumber
    val userDetails: MutableLiveData<List<userDetailsFormat>> = MutableLiveData<List<userDetailsFormat>>()
    var allAvailableUsers : MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    val chats: MutableLiveData<List<messageFormat>> = MutableLiveData<List<messageFormat>>()
    val unSeen: MutableLiveData<Int> = MutableLiveData<Int>()
    val notSeen: MutableLiveData<List<Pair<String,Int>>> = MutableLiveData<List<Pair<String,Int>>>()
    val callLogs: MutableLiveData<List<callLogFormat>> = MutableLiveData<List<callLogFormat>>()
    val statusList: MutableLiveData<MutableList<String>> = MutableLiveData<MutableList<String>>()
    val allStatusUsers: MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    val allStatusList: MutableLiveData<List<statusListFormat>> = MutableLiveData<List<statusListFormat>>()
    val allStatus: MutableLiveData<List<statusFormat>> = MutableLiveData<List<statusFormat>>()
    var loadAllStatus: MutableLiveData<List<Bitmap>> = MutableLiveData<List<Bitmap>>()
    val loadmystatusName: MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    var statusViews: MutableLiveData<MutableMap<String,Int>> = MutableLiveData<MutableMap<String, Int>>()
    var loadmysttus: MutableList<myStatusFormat> = mutableListOf()

    fun addNewUser(
        uid: String,
        uri: String?,
        Name: String,
        Phone: String
    ) {
        val details = detailFormat(uid,Name,Phone)
        database
            .document("users/$Phone")
            .set(details)
        if (uri != null) {
            storageref
                .child("phone/$Phone")
                .putFile(uri.toUri())
        }
    }

    fun getData(context: Context): MutableList<detailFormat> {
        val activeChats : MutableList<detailFormat> = mutableListOf()
        database
            .collection("whatsappclone/chats/$currentUser")
            .addSnapshotListener {
                    querySnapshot, firebaseFirestoreException ->
                querySnapshot?.let { querySnapshot ->
                    var documents = querySnapshot.documents
                    var chats = ArrayList<userDetailsFormat>()
                    documents.forEach{
                        var allchats = it.toObject(detailFormat::class.java)
                        allchats?.let {
                            val uds = userDetailsFormat(
                                name = allchats.name,
                                phone = allchats.phone,
                                uid = allchats.uid,
                                lastmsg = allchats.lastmsg,
                                msgdate = allchats.msgdate
                            )
                             it.phone?.let { it1 ->
                                 uds.image =loadImageBitmap(context, it1,"jpeg")
                            }
                            chats.add(uds)
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
        database.document("chats/$number")
            .set(mapOf(myPhone to 0))
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

    fun sendMessage(phone: String, message: String, unSeen: Int, messageType: Int) {
        val currentTime = LocalTime.now().toString()
        val currentDate = LocalDate.now().toString()
        var lastmsg = message
        if (messageType == 2 ) lastmsg = "image"
        val msg1 = messageFormat("1",message,currentTime,currentDate,messageType)
        database.document("chats/$myPhone/$phone/$currentDate$currentTime")
           .set(msg1)
        val msg2 = messageFormat("2", message, currentTime, currentDate,messageType)
        database.document("chats/$phone/$myPhone/$currentDate$currentTime")
            .set(msg2)
        database.document("whatsappclone/chats/$currentUser/$phone")
            .update(mapOf("lastmsg" to lastmsg, "msgdate" to currentDate))
        database.document("chats/$phone")
            .update(mapOf(myPhone to unSeen+1))
    }

    fun loadChat(phone: String) {
        database
            .collection("chats/$myPhone/$phone")
            .addSnapshotListener { value, error ->
                value?.let { value ->
                    val document = value.documents
                    val chatArray = ArrayList<messageFormat>()
                    document.forEach {
                        val perChat = it.toObject(messageFormat::class.java)
                        perChat?.let {
                            chatArray.add(perChat)
                        }
                    }
                    chats.value = chatArray
                }
            }
        database.document("chats/$myPhone")
            .update(mapOf(phone to 0))
    }

    fun unseen(phone: String) {
    var valll = 0
    database.document("chats/$phone")
        .addSnapshotListener { value, error ->
            value?.let {
                val field = value.data
                field?.let {
                    valll = it[myPhone].toString().toInt()
                }
            }
            unSeen.value = valll
        }
}

    fun myNotifications() {
        database.document("chats/$myPhone")
            .addSnapshotListener { value, error ->
                value?.let {
                    val fields = value.data
                    val array = ArrayList<Pair<String,Int>>()
                    fields?.let { fields->
                        fields.forEach { field->
                            field.let {
                                array.add(field.key.toString() to field.value.toString().toInt())
                            }
                        }
                    }
                    notSeen.value = array
                }
            }
    }

    fun addToCallLog(name: String, phone: String) {
        val currentTime = LocalTime.now().toString()
        val currentDate = LocalDate.now().toString()
        val  doc1 = callLogFormat(
            name = name,
            phone = phone,
            date = currentDate,
            time = currentTime,
            inOutMiss = "2",
            dateTime = "$currentDate$currentTime"
        )
        val doc2 = callLogFormat(
            name = name,
            phone = phone,
            date = currentDate,
            time = currentTime,
            inOutMiss = "1",
            dateTime = "$currentDate$currentTime"
        )
        database.collection("callLogs/calls/$myPhone")
            .add(doc1)
        database.collection("callLogs/calls/$phone")
            .add(doc2)
        database.document("whatsappclone/chats/$currentUser/$phone")
            .update(mapOf("lastmsg" to "voice call", "msgdate" to currentDate))
    }

    fun loadCallLog() {
        database.collection("callLogs/calls/$myPhone")
            .orderBy("dateTime",Query.Direction.DESCENDING)
            .limit(30)
            .addSnapshotListener { value, error ->
                value?.let {
                    val doc = value.documents
                    val callArray = ArrayList<callLogFormat>()
                    doc.let {
                        doc.forEach {
                            val item = it.toObject(callLogFormat::class.java)
                            item?.let {
                                callArray.add(item)
                            }
                        }
                    }
                    callLogs.value = callArray
                }
            }
    }

    fun sendImage(image: String, name: String, phone: String, extension: String, context: Context) {
        storageref
            .child("chats/$myPhone/$name")
            .putFile(image.toUri())
        storageref
            .child("chats/$phone/$name")
            .putFile(image.toUri())
        var name1 = name
        name1 = "$name.$extension"
        val contentResolver: ContentResolver = context.contentResolver
        val source: ImageDecoder.Source = ImageDecoder.createSource(contentResolver, image.toUri())
        var bitmap = ImageDecoder.decodeBitmap(source)
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = context.openFileOutput(name1, Context.MODE_PRIVATE)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream)
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveImage(context: Context, name1: String, extension: String) {
        storageref
            .child("phone/$name1")
            .getBytes(Long.MAX_VALUE)
            .addOnSuccessListener {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                var name = name1
                name = "$name.$extension"
                val fileOutputStream: FileOutputStream
                try {
                    fileOutputStream = context.openFileOutput(name, Context.MODE_PRIVATE)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream)
                    fileOutputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
    }

    fun loadImageBitmap(context: Context, name1: String, extension: String): Bitmap? {
        val name = "$name1.$extension"
        val fileInputStream : FileInputStream
        var bitmap: Bitmap? = null
        try{
            fileInputStream = context.openFileInput(name);
            bitmap = BitmapFactory.decodeStream(fileInputStream)
            fileInputStream.close()
        } catch(e: Exception) {
            e.printStackTrace()
            saveImage(context,name1,"jpeg")
        }
        return bitmap
    }

    fun sendStatus(name: String, image: String, context: Context, extension: String) {
        val currentTime = LocalTime.now().toString()
        val name = "$myPhone$currentTime"
        storageref.child("status/$myPhone/$name").putFile(image.toUri())

        var name1 = name
        name1 = "$name.$extension"
        val contentResolver: ContentResolver = context.contentResolver
        val source: ImageDecoder.Source = ImageDecoder.createSource(contentResolver, image.toUri())
        var bitmap = ImageDecoder.decodeBitmap(source)
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = context.openFileOutput(name1, Context.MODE_PRIVATE)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream)
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        database.document("status names/$myPhone").update(currentTime.dropLast(4) ,name)

        database.document("statusViews/$myPhone").update(currentTime.dropLast(4), 0)

        database.collection("whatsappclone/chats/$currentUser")
            .addSnapshotListener { value, error ->
                value?.let {
                    val documents = value.documents
                    documents.forEach { document ->
                        database.document("status/${document.id}")
                            .update(mapOf(myPhone to true))
                    }
                }
            }
    }

    fun loadMyStatus(context: Context): MutableList<Bitmap> {
        val BitmapList = mutableListOf<Bitmap>()
        database.document("status names/$myPhone")
            .addSnapshotListener{ data, error ->
                data?.let {
                   val List = mutableListOf<String>()
                    val doc = it.data
                    doc?.forEach{
                        List.add(it.value.toString())
                    }
                    statusList.value = List
                }
            }

        statusList.value?.sorted()?.forEach {
            val name = "$it.jpeg"
            val fileInputStream : FileInputStream
            var bitmap: Bitmap? = null
            try{
                fileInputStream = context.openFileInput(name)
                bitmap = BitmapFactory.decodeStream(fileInputStream)
                fileInputStream.close()
                BitmapList.add(bitmap)
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
        loadAllStatus.value = BitmapList
        return BitmapList
    }

    fun allStatusUsers() {
        database.document("status/$myPhone")
            .get()
            .addOnSuccessListener {
                val listOfUsers = mutableListOf<String>()
                it.data?.forEach {
                    if (it.value as Boolean) listOfUsers.add(it.key)
                }
                allStatusUsers.value = listOfUsers
            }
    }

    fun getAllStatusNames(allStatusUser: List<String>) {
        database.collection("status names")
            .addSnapshotListener{ value, error ->
                val documents = value?.documents
                documents?.let { documents ->
                    val statusList = ArrayList<statusListFormat>()
                    documents.forEach { document ->
                        if (allStatusUser.contains(document.id)){
                            val list = ArrayList<String>()
                            document.data?.forEach {
                                list.add(it.value.toString())
                            }
                            statusList.add(statusListFormat(
                                phone = document.id,
                                allNames = list,
                                name = list.first().toString().dropLast(12)
                            ))
                        }
                    }
                    allStatusList.value = statusList
                }
            }
    }


    fun saveStatus(phone: String, name: String, context: Context) {
        storageref.child("status/$phone/$name")
            .getBytes(Long.MAX_VALUE)
            .addOnSuccessListener {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                var name1 = name
                name1 = "$name1.jpeg"
                val fileOutputStream: FileOutputStream
                try {
                    fileOutputStream = context.openFileOutput(name1, Context.MODE_PRIVATE)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream)
                    fileOutputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
    }


    fun loadAllStatus(allStatusList: List<statusListFormat>, context: Context) {
        val eachStatusFormat = mutableListOf<statusFormat>()
        allStatusList.forEach { docs ->
            val statusDocs = mutableListOf<Bitmap>()
            docs.allNames?.forEach { doc ->
                val name = "$doc.jpeg"
                val fileInputStream : FileInputStream
                var bitmap: Bitmap? = null
                try{
                    fileInputStream = context.openFileInput(name);
                    bitmap = BitmapFactory.decodeStream(fileInputStream)
                    statusDocs.add(bitmap)
                    fileInputStream.close()
                } catch(e: Exception) {
                    e.printStackTrace()
                    saveStatus(phone = docs.phone.toString(),name = doc, context = context)
                }
            }

            eachStatusFormat.add(
                statusFormat(
                    phone = docs.phone,
                    status = statusDocs,
                    time =docs.name?.takeLast(12)!!.dropLast(7),
                    name = docs.phone
                )
            )
            allStatus.value = eachStatusFormat
        }
    }

    fun loadStatusImages(context: Context, statusNames: Array<String>?): List<Bitmap> {
        val statusDocs = mutableListOf<Bitmap>()
        statusNames?.forEach { doc ->
            val name = "$doc.jpeg"
            val fileInputStream : FileInputStream
            var bitmap: Bitmap? = null
            try{
                fileInputStream = context.openFileInput(name);
                bitmap = BitmapFactory.decodeStream(fileInputStream)
                statusDocs.add(bitmap)
                fileInputStream.close()
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
        return statusDocs
    }

    fun statusViewed(name: String) {
        database.document("status/$myPhone")
            .update(mapOf(name to false))
    }

    fun updateView(phone: String, name: String) {
        database.document("statusViews/$phone")
            .get()
            .addOnSuccessListener {  snapShot ->
                val docs = snapShot.data
                docs?.forEach {  field->
                   if (field.key == name.dropLast(4).drop(13)){
                       database.document("statusViews/$phone")
                           .update(name.dropLast(4).drop(13),field.value.toString().toInt()+1)
                   }
               }
            }
    }

    fun deleteStatus(name: String, context: Context) {
        database.document("status names/$myPhone")
            .update(mapOf(name to FieldValue.delete()))
        database.document("statusViews/$myPhone")
            .update(mapOf(name to FieldValue.delete()))
        try {
            val file: File = context.getFileStreamPath(name)
            file.delete()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun loadmystatusName() {
        database.document("status names/$myPhone")
            .get()
            .addOnSuccessListener { docSnapShot ->
                val doc = mutableListOf<String>()
                docSnapShot.data?.forEach {
                    doc.add(it.value.toString())
                }
                loadmystatusName.value = doc
            }
    }

    fun getViewDetails() {
        database.document("statusViews/$myPhone")
            .get()
            .addOnSuccessListener { docSnapShot ->
                val viewDetails = mutableMapOf<String,Int>()
                docSnapShot.data?.forEach { field->
                        viewDetails.set(field.key, field.value.toString().toInt())
                }
                statusViews.value = viewDetails
            }
    }

    fun loadmysttus(names: List<String>, viewDetails: MutableMap<String, Int>, context: Context): MutableList<myStatusFormat> {
        val loadmysttuss = mutableListOf<myStatusFormat>()
        names.forEach { name ->
            val name = "$name.jpeg"
            val fileInputStream: FileInputStream
            var bitmap: Bitmap? = null
            try {
                fileInputStream = context.openFileInput(name);
                bitmap = BitmapFactory.decodeStream(fileInputStream)
                loadmysttuss.add(
                    myStatusFormat(
                        image = bitmap,
                        name = name,
                        time = name.dropLast(12).drop(13),
                        view = viewDetails[name.drop(13).dropLast(9)].toString().toInt()
                    )
                )
                fileInputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            loadmysttus = loadmysttuss
        }
        return loadmysttuss
    }
}