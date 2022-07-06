package com.example.whatsapp_clone.mainPage

import android.annotation.SuppressLint
import android.provider.CallLog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.modelItems.callLogModel
import kotlin.time.ExperimentalTime

@SuppressLint("Range", "DefaultLocale")
@Preview(showBackground = true)
@Composable
fun callPage() {
    val addCallImg = painterResource(id = R.drawable.add_call)

    val context = LocalContext.current
    val contentresolver = context.contentResolver
    val uri = CallLog.Calls.CONTENT_URI
    val cursor  = contentresolver.query(uri, null, null, null, null, null)


    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.height(10.dp))
        if (cursor != null) {
            while (cursor.moveToNext()) {
                callLogModel(
                    Name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)),
                    duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION)),
                    type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)),
                )
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