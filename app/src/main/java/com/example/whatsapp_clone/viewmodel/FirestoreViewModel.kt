package com.example.whatsapp_clone.viewmodel


import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
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

class FirestoreViewModel: ViewModel() {

    private val database = Firebase.firestore
    private val storageRef = Firebase.storage.reference
    private val currentUser = FirebaseAuth.getInstance().uid
    private val myPhone = FirebaseAuth.getInstance().currentUser?.phoneNumber
    private val statusList: MutableLiveData<MutableList<String>> = MutableLiveData<MutableList<String>>()
    private var loadAllStatus: MutableLiveData<List<Bitmap>> = MutableLiveData<List<Bitmap>>()
    private var loadMyStatus: MutableList<myStatusFormat> = mutableListOf()
    var myName : MutableLiveData<String> = MutableLiveData<String>()
    val userDetails: MutableLiveData<List<userDetailsFormat>> = MutableLiveData<List<userDetailsFormat>>()
    var allAvailableUsers : MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    val chats: MutableLiveData<List<messageFormat>> = MutableLiveData<List<messageFormat>>()
    val unSeen: MutableLiveData<Int> = MutableLiveData<Int>()
    val notSeen: MutableLiveData<List<Pair<String,Int>>> = MutableLiveData<List<Pair<String,Int>>>()
    val callLogs: MutableLiveData<List<callLogFormat>> = MutableLiveData<List<callLogFormat>>()
    val allStatusUsers: MutableLiveData<MutableMap<String, Boolean>> = MutableLiveData<MutableMap<String, Boolean>>()
    val allStatusList: MutableLiveData<List<statusListFormat>> = MutableLiveData<List<statusListFormat>>()
    val allStatus: MutableLiveData<List<statusFormat>> = MutableLiveData<List<statusFormat>>()
    val loadMyStatusName: MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    var statusViews: MutableLiveData<MutableMap<String,Int>> = MutableLiveData<MutableMap<String, Int>>()

    /**
     * Adds New User details into firebase firestore and store user image into firebase storage
     */
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
            storageRef
                .child("phone/$Phone")
                .putFile(uri.toUri())
        }
    }

    /**
     * Updates user profile picture stored in the firebase storage
     */
    fun updateProfilePic(uri: Uri?, context:Context) {
        storageRef
            .child("phone/$myPhone")
            .putFile(uri!!)
        val name1: String?
        name1 = "$myPhone.jpeg"
        val contentResolver: ContentResolver = context.contentResolver
        val source: ImageDecoder.Source = ImageDecoder.createSource(contentResolver, uri)
        val bitmap = ImageDecoder.decodeBitmap(source)
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = context.openFileOutput(name1, Context.MODE_PRIVATE)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream)
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     *  Updates user name stored in firebase firestore
     */
    fun updateMyName(newName: String?) {
        database.document("users/$myPhone")
            .update("name", newName)
    }

    /**
     *  Fetches current user name from the firestore
     */
    fun getMyName() {
       database
            .document("users/$myPhone")
            .get()
            .addOnSuccessListener {
                val details = it.toObject(detailFormat::class.java)
                myName.value = details?.name
            }
    }

    /**
     *  Fetches all the available chat for a this user
     */
    fun getData(context: Context): MutableList<detailFormat> {
        val activeChats : MutableList<detailFormat> = mutableListOf()
        database
            .collection("whatsappclone/chats/$currentUser")
            .addSnapshotListener {
                    querySnapshot, _ ->
                querySnapshot?.let { it ->
                    val documents = it.documents
                    val chats = ArrayList<userDetailsFormat>()
                    documents.forEach{ it1 ->
                        val allChats = it1.toObject(detailFormat::class.java)
                        allChats?.let {
                            val uds = userDetailsFormat(
                                name = allChats.name,
                                phone = allChats.phone,
                                uid = allChats.uid,
                                lastmsg = allChats.lastmsg,
                                msgdate = allChats.msgdate
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

    /**
     *  Fetches all the Users that have registered to this application
     */
    fun getAllUsers() {
        val allUsers: MutableList<String> = mutableListOf()
         database.collection("users")
             .addSnapshotListener { querySnapshot, _ ->
                 querySnapshot?.let {
                     querySnapshot.documents.forEach { doc->
                         allUsers.add(doc.id)
                     }
                     allAvailableUsers.value = allUsers
                 }
             }
    }

    /**
     *  Adds the selected person as a new chat to the current user
     */
    private fun addNewChat(name:String, number: String) {
        database
            .document("whatsappclone/chats/$currentUser/$number")
            .set(detailFormat(name = name, phone = number))
        database.document("chats/$number")
            .set(mapOf(myPhone to 0))
    }

    /**
     *  Checks weather a particular chat is already present or not
     */
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

    /**
     *  Sends or Saves the user message to the both sender and receiver database,
     *  updates the unseen message, and also changes the last message sent value
     */
    fun sendMessage(phone: String, message: String, unSeen: Int, messageType: Int) {
        val currentTime = LocalTime.now().toString()
        val currentDate = LocalDate.now().toString()
        var lastMsg = message
        if (messageType == 2 ) lastMsg = "image"
        val msg1 = messageFormat("1",message,currentTime,currentDate,messageType)
        database.document("chats/$myPhone/$phone/$currentDate$currentTime")
           .set(msg1)
        val msg2 = messageFormat("2", message, currentTime, currentDate,messageType)
        database.document("chats/$phone/$myPhone/$currentDate$currentTime")
            .set(msg2)
        database.document("whatsappclone/chats/$currentUser/$phone")
            .update(mapOf("lastmsg" to lastMsg, "msgdate" to currentDate))
        database.document("chats/$phone")
            .update(mapOf(myPhone to unSeen+1))
    }

    /**
     *  Fetches all the data from a particular chat and updates the unseen messages to zero
     */
    fun loadChat(phone: String) {
        database
            .collection("chats/$myPhone/$phone")
            .addSnapshotListener { value, _ ->
                value?.let { it ->
                    val document = it.documents
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

    /**
     *  Fetches the unseen message of a particular person
     */
    fun unseen(phone: String) {
    var value1 = 0
    database.document("chats/$phone")
        .addSnapshotListener { value, _ ->
            value?.let {
                val field = value.data
                field?.let {
                    value1 = it[myPhone].toString().toInt()
                }
            }
            unSeen.value = value1
        }
}

    /**
     *  Fetches the number of all unseen message from all available chats
     */
    fun myNotifications() {
        database.document("chats/$myPhone")
            .addSnapshotListener { value, _ ->
                value?.let {
                    val fields = value.data
                    val array = ArrayList<Pair<String,Int>>()
                    fields?.let {
                        it.forEach { eachField->
                            eachField.let {
                                array.add(eachField.key.toString() to eachField.value.toString().toInt())
                            }
                        }
                    }
                    notSeen.value = array
                }
            }
    }

    /**
     *  Save the Last call made detail to firebase firestore database,
     *  and update the last message as voice call for both caller and receiver
     */
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

    /**
     *  Fetches all the call log detail for the current user.
     */
    fun loadCallLog() {
        database.collection("callLogs/calls/$myPhone")
            .orderBy("dateTime",Query.Direction.DESCENDING)
            .limit(30)
            .addSnapshotListener { value, _ ->
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

    /**
     *  Saves the Image send to firebase storage to both sender and receiver database,
     *  and save it locally on sender device separately
     */
    fun sendImage(image: String, name: String, phone: String, extension: String, context: Context) {
        storageRef
            .child("chats/$myPhone/$name")
            .putFile(image.toUri())
        storageRef
            .child("chats/$phone/$name")
            .putFile(image.toUri())
        val name1 = "$name.$extension"
        val contentResolver: ContentResolver = context.contentResolver
        val source: ImageDecoder.Source = ImageDecoder.createSource(contentResolver, image.toUri())
        val bitmap = ImageDecoder.decodeBitmap(source)
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = context.openFileOutput(name1, Context.MODE_PRIVATE)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream)
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     *  Fetches an image from the firebase storage database and stores it locally on user device
     */
    fun saveImage(context: Context, name1: String, extension: String) {
        storageRef
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

    /**
     *  Fetches the image stored the device locally
     */
    fun loadImageBitmap(context: Context, name1: String, extension: String): Bitmap? {
        val name = "$name1.$extension"
        val fileInputStream : FileInputStream
        var bitmap: Bitmap? = null
        try{
            fileInputStream = context.openFileInput(name)
            bitmap = BitmapFactory.decodeStream(fileInputStream)
            fileInputStream.close()
        } catch(e: Exception) {
            e.printStackTrace()
            saveImage(context,name1,"jpeg")
        }
        return bitmap
    }

    /**
     *  Saves the status images locally, save the image into sender and receiver firebase storage database,
     *  and updates the receiver that a new status has been added
     */
    fun sendStatus(image: String, context: Context, extension: String) {
        val currentTime = LocalTime.now().toString()
        val name = "$myPhone$currentTime"
        storageRef.child("status/$myPhone/$name").putFile(image.toUri())

        val name1 = "$name.$extension"
        val contentResolver: ContentResolver = context.contentResolver
        val source: ImageDecoder.Source = ImageDecoder.createSource(contentResolver, image.toUri())
        val bitmap = ImageDecoder.decodeBitmap(source)
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
            .addSnapshotListener { value, _ ->
                value?.let {
                    val documents = value.documents
                    documents.forEach { document ->
                        database.document("status/${document.id}")
                            .update(mapOf(myPhone to true))
                    }
                }
            }
    }

    /**
     *  Fetches the name of all my status image and loads all of the
     *  current user status images that is stored locally
     */
    fun loadMyStatus(context: Context): MutableList<Bitmap> {
        val bitmapList = mutableListOf<Bitmap>()
        database.document("status names/$myPhone")
            .addSnapshotListener{ data, _ ->
                data?.let {
                   val list = mutableListOf<String>()
                    val doc = it.data
                    doc?.forEach{ it1 ->
                        list.add(it1.value.toString())
                    }
                    statusList.value = list
                }
            }

        statusList.value?.sorted()?.forEach {
            val name = "$it.jpeg"
            val fileInputStream : FileInputStream
            val bitmap: Bitmap?
            try{
                fileInputStream = context.openFileInput(name)
                bitmap = BitmapFactory.decodeStream(fileInputStream)
                fileInputStream.close()
                bitmapList.add(bitmap)
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
        loadAllStatus.value = bitmapList
        return bitmapList
    }

    /**
     *  Fetches all the names of users with status from the firebase firestore
     */
    fun allStatusUsers() {
        database.document("status/$myPhone")
            .get()
            .addOnSuccessListener {
                val listOfUsers = mutableMapOf<String, Boolean>()
                it.data?.forEach { it1->
                    listOfUsers[it1.key] = it1.value as Boolean
                }
                allStatusUsers.value = listOfUsers
            }
    }

    /**
     *  Fetches the names of status images for each user as a list from the firebase firestore
     */
    fun getAllStatusNames(allStatusUser: Map<String, Boolean>) {
        database.collection("status names")
            .addSnapshotListener{ value, _ ->
                val documents = value?.documents
                documents?.let { allDocuments ->
                    val statusList = ArrayList<statusListFormat>()
                    allDocuments.forEach { document ->
                        if (allStatusUser.contains(document.id)){
                            val list = ArrayList<String>()
                            document.data?.forEach {
                                list.add(it.value.toString())
                            }
                            statusList.add(statusListFormat(
                                phone = document.id,
                                allNames = list,
                                name = list.first().toString().dropLast(12),
                                viewed = allStatusUser[document.id]
                            ))
                        }
                    }
                    allStatusList.value = statusList
                }
            }
    }


    /**
     * Fetches and Saves a particular status image from the firebase storage
     */
    private fun saveStatus(phone: String, name: String, context: Context) {
        storageRef.child("status/$phone/$name")
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


    /**
     *  Loads all the every users status
     */
    fun loadAllStatus(allStatusList: List<statusListFormat>, context: Context) {
        val eachStatusFormat = mutableListOf<statusFormat>()
        allStatusList.forEach { docs ->
            val statusDocs = mutableListOf<Bitmap>()
            docs.allNames?.forEach { doc ->
                val name = "$doc.jpeg"
                val fileInputStream : FileInputStream
                val bitmap: Bitmap?
                try{
                    fileInputStream = context.openFileInput(name)
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
                    name = docs.phone,
                    viewed = docs.viewed
                )
            )
            allStatus.value = eachStatusFormat
        }
    }

    /**
     *  Returns all the list of image required
     */
    fun loadStatusImages(context: Context, statusNames: Array<String>?): List<Bitmap> {
        val statusDocs = mutableListOf<Bitmap>()
        statusNames?.forEach { doc ->
            val name = "$doc.jpeg"
            val fileInputStream : FileInputStream
            val bitmap: Bitmap?
            try{
                fileInputStream = context.openFileInput(name)
                bitmap = BitmapFactory.decodeStream(fileInputStream)
                statusDocs.add(bitmap)
                fileInputStream.close()
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
        return statusDocs
    }

    /**
     *  Updates that a particular status has been viewed by setting its value to false
     */
    fun statusViewed(name: String) {
        database.document("status/$myPhone")
            .update(mapOf(name to false))
    }

    /**
     *  Increments the number of view for a status when we each particular status image
     */
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

    /**
     *  Delete a image from the firebase as well as from the local machine
     */
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

    /**
     *  Fetches the current user status image names
     */
    fun loadMyStatusName() {
        database.document("status names/$myPhone")
            .get()
            .addOnSuccessListener { docSnapShot ->
                val doc = mutableListOf<String>()
                docSnapShot.data?.forEach {
                    doc.add(it.value.toString())
                }
                loadMyStatusName.value = doc
            }
    }

    /**
     *  Fetches the number of views for each status image
     */
    fun getViewDetails() {
        database.document("statusViews/$myPhone")
            .get()
            .addOnSuccessListener { docSnapShot ->
                val viewDetails = mutableMapOf<String,Int>()
                docSnapShot.data?.forEach { field->
                    viewDetails[field.key] = field.value.toString().toInt()
                }
                statusViews.value = viewDetails
            }
    }

    /**
     *  Returns all of the current user images in a particular format
     */
    fun loadMyStatus(names: List<String>, viewDetails: MutableMap<String, Int>, context: Context): MutableList<myStatusFormat> {
        val loadMyStatus = mutableListOf<myStatusFormat>()
        names.forEach { eachName ->
            val name = "$eachName.jpeg"
            val fileInputStream: FileInputStream
            val bitmap: Bitmap?
            try {
                fileInputStream = context.openFileInput(name)
                bitmap = BitmapFactory.decodeStream(fileInputStream)
                loadMyStatus.add(
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
            this.loadMyStatus = loadMyStatus
        }
        return loadMyStatus
    }
}