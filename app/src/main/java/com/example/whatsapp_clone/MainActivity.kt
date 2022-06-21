package com.example.whatsapp_clone

import android.app.ActionBar
import android.os.Bundle
import android.widget.TabHost
import android.widget.TableRow
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    var pagerState = rememberPagerState()
    val couroutineScope = rememberCoroutineScope()
    val tabsList = listOf("CAMERA", "CHATS", "STATUS", "CALLS")
    val cameraImg = painterResource(id = R.drawable.camera_img)

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFF00827f),
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(text = "Whatsapp", fontSize = 20.sp, color = Color.White, modifier = Modifier.padding(10.dp))
        }
        TabRow(selectedTabIndex = selectedIndex,
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