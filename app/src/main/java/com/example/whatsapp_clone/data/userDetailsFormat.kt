package com.example.whatsapp_clone.data

import android.graphics.Bitmap

data class userDetailsFormat (
    var uid: String? = null,
    var name: String? = null,
    var phone: String? = null,
    var lastmsg: String? = null,
    var msgdate: String? = null,
    var image: Bitmap? = null
)