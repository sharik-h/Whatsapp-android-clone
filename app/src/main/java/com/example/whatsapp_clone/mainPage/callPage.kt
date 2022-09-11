package com.example.whatsapp_clone.mainPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.modelItems.callLogModel
import com.example.whatsapp_clone.viewmodel.firestoreViewModel

@Composable
fun callPage(viewModel: firestoreViewModel) {

    viewModel.loadCallLog()
    val addCallImg = painterResource(id = R.drawable.add_call)
    val callLog by viewModel.callLogs.observeAsState(initial = emptyList())


    Column(
        Modifier
            .fillMaxSize()) {
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn() {
            items(items = callLog) {
                it.inOutMiss?.let { it1 ->
                    it.time?.let { it2 ->
                        callLogModel(
                            Name = it.name!!,
                            type = it1,
                            time = it2,
                            phone = it.phone,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
     FloatingActionButton(
         onClick = { /*TODO*/ },
         backgroundColor = Color(0xFF008268)
     ) {
      Image(painter = addCallImg, contentDescription = "")
     }
    }
}