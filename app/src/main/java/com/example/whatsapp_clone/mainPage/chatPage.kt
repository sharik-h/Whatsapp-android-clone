package com.example.whatsapp_clone.mainPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.whatsapp_clone.modelItems.chatItemModel
import com.example.whatsapp_clone.viewmodel.firestoreViewModel


@Composable
fun chatPage(viewModel: firestoreViewModel) {

    viewModel.getData()
    val userDetails by viewModel.userDetails.observeAsState(initial = emptyList())

    Column(Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)) {
            items(items = userDetails) {
                chatItemModel(it)
            }
        }
    }

}