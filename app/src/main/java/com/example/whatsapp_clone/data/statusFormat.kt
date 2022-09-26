package com.example.whatsapp_clone.data

import android.graphics.Bitmap

data class statusFormat (
    val name: String? = null,
    val phone: String? = null,
    val time: String,
    val status: MutableList<Bitmap>? = null,
    val viewed: Boolean? = false
)