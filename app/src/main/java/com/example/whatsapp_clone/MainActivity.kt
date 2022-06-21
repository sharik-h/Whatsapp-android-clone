package com.example.whatsapp_clone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.ui.theme.Whatsapp_cloneTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Whatsapp_cloneTheme {
                TabSample()
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabSample() {
    var selectedIndex by remember { mutableStateOf(1) }
    var pagerState = rememberPagerState(1)
    val couroutineScope = rememberCoroutineScope()
    var expanded1 by remember { mutableStateOf(false)}
    var expanded2 by remember { mutableStateOf(false)}
    var expanded3 by remember { mutableStateOf(false)}
    val tabsList = listOf("CAMERA", "CHATS", "STATUS", "CALLS")
    val cameraImg = painterResource(id = R.drawable.camera_img)
    val optionsImg = painterResource(id = R.drawable.option_img)
    val searchImg = painterResource(id = R.drawable.search_img)


    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFF00827f),
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
                when(pagerState.currentPage) {
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
                    TextButton(onClick = { /*TODO*/ }) {
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
            backgroundColor = Color(0xFF00827f),
            indicator = {  tabPosition ->
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
                            couroutineScope.launch {
                                pagerState.animateScrollToPage(selectedIndex)
                            } },
                        modifier = Modifier.width( 10.dp),
                        icon = { Image(painter = cameraImg, contentDescription = "") },
                    )
                }else {
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            selectedIndex = index
                            couroutineScope.launch {
                                pagerState.animateScrollToPage(selectedIndex)
                            }
                        },
                        text = {
                            Text(
                                text = text,
                                fontWeight = FontWeight.Bold,
                                color = if (pagerState.currentPage == index) Color.White
                                else Color(0xFFB8B8B8)
                            )
                        }
                    )
                }
            }
        }
        HorizontalPager(count = 4, state = pagerState, ) {
               Text(text = tabsList[pagerState.currentPage])
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TabSample()
}