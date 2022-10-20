package com.example.whatsapp_clone.mainPage

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.chatActivity.attachmentActivity
import com.example.whatsapp_clone.settingActivity
import com.example.whatsapp_clone.ui.theme.green60
import com.example.whatsapp_clone.ui.theme.green80
import com.example.whatsapp_clone.viewmodel.FirestoreViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


class TabActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: FirestoreViewModel by viewModels()
        setContent {
             TabSample(viewModel)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabSample(viewModel: FirestoreViewModel) {
    var selectedIndex by remember { mutableStateOf(1) }
    val pagerState = rememberPagerState(1)
    val coroutineScope = rememberCoroutineScope()
    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expanded3 by remember { mutableStateOf(false) }
    val tabsList = listOf("CAMERA", "CHATS", "STATUS", "CALLS")
    val cameraImg = painterResource(id = R.drawable.camera_img)
    val optionsImg = painterResource(id = R.drawable.option_img)
    val searchImg = painterResource(id = R.drawable.search_img)
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val clauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) {
            val bytes = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path =
                MediaStore.Images.Media.insertImage(context.contentResolver, it, "Title", null)
            imageUri = Uri.parse(path.toString())
            context.startActivity(
                Intent(context, attachmentActivity::class.java)
                    .putExtra("imageUri", imageUri.toString())
            )
        }

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = green60,
        ) {
            Text(
                text = "Whatsapp",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(10.dp)
            )
            Spacer(modifier = Modifier.weight(0.5f))
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = searchImg, contentDescription = "")
            }
            IconButton(onClick = {
                when (pagerState.currentPage) {
                    1 -> expanded1 = true
                    2 -> expanded2 = true
                    3 -> expanded3 = true
                }

            }) {
                DropdownMenu(
                    expanded = expanded1,
                    onDismissRequest = { expanded1 = false },
                    Modifier.width(180.dp)
                ) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "New group")
                    }
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "New broadcast")
                    }
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Linked devices")
                    }
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Starred messages")
                    }
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Payments")
                    }
                    TextButton(onClick = {
                        context.startActivity(
                            Intent(
                                context,
                                settingActivity::class.java
                            )
                        )
                    }) {
                        Text(text = "Settings")
                    }
                }
                DropdownMenu(
                    expanded = expanded2,
                    onDismissRequest = { expanded2 = false },
                    Modifier.width(180.dp)

                ) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Status privacy")
                    }
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Settings")
                    }
                }
                DropdownMenu(
                    expanded = expanded3,
                    onDismissRequest = { expanded3 = false },
                    Modifier.width(180.dp)
                ) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Clear call log")
                    }
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Settings")
                    }
                }
                Image(painter = optionsImg, contentDescription = "")
            }
        }
        TabRow(
            selectedTabIndex = selectedIndex,
            backgroundColor = green60,
            indicator = { tabPosition ->
                TabRowDefaults.Indicator(
                    color = Color.White,
                    modifier = Modifier.pagerTabIndicatorOffset(
                        pagerState = pagerState,
                        tabPositions = tabPosition,
                    )
                )
            }
        ) {

            tabsList.forEachIndexed { index, text ->
                if (index == 0) {
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            selectedIndex = index
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(selectedIndex)
                            }
                        },
                        modifier = Modifier.width(10.dp),
                        icon = { Image(painter = cameraImg, contentDescription = "") },
                    )
                } else {
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            selectedIndex = index
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(selectedIndex)
                            }
                        },
                        text = {
                            Text(
                                text = text,
                                fontWeight = FontWeight.Bold,
                                color = if (pagerState.currentPage == index) Color.White
                                else green80
                            )
                        }
                    )
                }
            }
        }
        HorizontalPager(count = 4, state = pagerState) {
            if (tabsList[pagerState.currentPage] == "CALLS") callPage(viewModel)
            else if (tabsList[pagerState.currentPage] == "CHATS") chatPage(viewModel)
            else if (tabsList[pagerState.currentPage] == "STATUS") statusPage(viewModel)
            else if (tabsList[pagerState.currentPage] == "CAMERA") clauncher.launch()
            else Text(text = tabsList[pagerState.currentPage])
        }
    }
}
