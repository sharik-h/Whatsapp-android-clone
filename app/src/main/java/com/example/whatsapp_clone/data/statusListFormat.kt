package com.example.whatsapp_clone.data

data class statusListFormat (
    val name: String? = null,
    val phone: String? = null,
    val allNames: MutableList<String>? = null,
    val viewed: Boolean? = false,
)