package com.example.whatsapp_clone.loginPages

import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.R
import com.example.whatsapp_clone.ui.theme.green60

@Preview(showBackground = true)
@Composable
fun numberPage() {
    var number by remember{ mutableStateOf("")}
    var n1 by remember{ mutableStateOf("+91")}
    var open by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("India") }
    val context = LocalContext.current
    val arrowImg = painterResource(id = R.drawable.arrow_drop_down)

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        ) {
            Text(
                text = "Enter your phone number",
                color = green60,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = "WhatsApp will need to verify your phone number.",
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
            .width(260.dp)
            .height(40.dp)
            .clickable { open = true },
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = selected,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Image(painter = arrowImg, contentDescription = "")
            Divider(
                thickness = 1.dp,
                color = green60,
                modifier = Modifier.padding(top = 30.dp)
            )
            DropdownMenu(
                expanded = open,
                onDismissRequest = { open = !open },
                modifier = Modifier.width(260.dp)
            ) {
                TextButton(onClick = {
                    selected = "India"
                    open = false
                    n1 = "+91" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "India")
                }
                TextButton(onClick = {
                    selected = "Pakistan"
                    open = false
                    n1 = "+92" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Pakistan")
                }
                TextButton(onClick = {
                    selected = "China"
                    open = false
                    n1 = "+81" },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "China")
                }
            }
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Box(modifier = Modifier.width(60.dp), contentAlignment = Alignment.BottomCenter) {
                Text(text = n1, fontSize = 25.sp)
                Divider(thickness = 1.dp, color = green60)
            }
            TextField(
                value = number,
                onValueChange = { number = it },
                singleLine = true,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(200.dp),
                textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = green60,
                    unfocusedIndicatorColor = green60,
                    cursorColor = green60,
                    textColor = green60
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (number.length == 10) {
                            context.startActivity( Intent(context, AuthOtp::class.java).putExtra("number", number))
                        }
                    }
                )
            )

        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            onClick = {
                if (number.length == 10) {
                    context.startActivity( Intent(context, AuthOtp::class.java).putExtra("number", number))
                }
            } ,
            shape = RoundedCornerShape(10),
            colors = ButtonDefaults.buttonColors(green60)
        ) {
            Text(text = "Next", color = Color.White)
        }
    }
}