package com.example.whatsapp_clone.loginPages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.whatsapp_clone.LoginNavigation.loginScreen

@Composable
fun agreeTerms(navHostController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to WhatsApp",
            fontSize = 34.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF008268)
        )
        Spacer(modifier = Modifier.height(450.dp))
        Text(
            text = "Read our Privacy Policy. Tap 'Agree and continue' to accept the Terms of Service.",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier.padding(10.dp),
            textAlign = TextAlign.Center,
            color = Color(0xFF949494)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = { navHostController.navigate(loginScreen.numberPage.route) },
            shape = RoundedCornerShape(10),
            modifier = Modifier.width(275.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF008268))
        ) {
            Text(text = "AGREE AND CONTINUE", color = Color.White)
        }
    }
}