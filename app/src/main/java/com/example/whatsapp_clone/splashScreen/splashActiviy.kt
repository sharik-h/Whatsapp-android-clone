package com.example.whatsapp_clone.splashScreen

import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import androidx.compose.material.*
import androidx.compose.ui.text.style.TextAlign
import com.example.whatsapp_clone.LoginNavigation.loginScreen
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.mainPage.TabActivity


@Composable
fun Splash(navHostController: NavHostController) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 3000)
    )
    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(2000)
        navHostController.popBackStack()
        val user = FirebaseAuth.getInstance().currentUser?.uid
        if (user != null) {
            context.startActivity(Intent(context, TabActivity::class.java))
        }else{
            navHostController.navigate(loginScreen.agreeTerms.route)
        }
    }
    SplashPreview(alpha = alphaAnimation.value)
}

@Composable
fun SplashPreview(alpha: Float) {
    Column(modifier = Modifier
        .fillMaxSize()
        .alpha(alpha = alpha)
        .background(Color.White)
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val logo: Painter = painterResource(id = R.drawable.whatsapp_svg)
        val metaImg = painterResource(id = R.drawable.meta_img)
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = logo, contentDescription = "", modifier = Modifier.size(80.dp))
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "from",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Image(painter = metaImg, contentDescription = "", modifier = Modifier
                .size(16.dp)
                .padding(top = 2.dp))
            Text(
                text = "  Meta",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}





