package com.example.whatsapp_clone.loginPages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsapp_clone.MainActivity
import com.example.whatsapp_clone.viewmodel.FirestoreViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class AuthOtp(): ComponentActivity() {

    lateinit var Sotp: String
    var sendStatus = mutableStateOf(0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        val phone = intent.getStringExtra("number")

        setContent {
            if (phone != null) {
                authOtp(phone)
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phone")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                        Toast.makeText(applicationContext, "Verified", Toast.LENGTH_SHORT).show()
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        Toast.makeText(applicationContext, "failed sending opt", Toast.LENGTH_SHORT).show()
                        sendStatus.value = -1
                    }

                    override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                        super.onCodeSent(p0, p1)
                        Sotp = p0
                        sendStatus.value = 1
                    }
                })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun SignInWithCred(otp: String) {
        val credential = PhoneAuthProvider.getCredential(Sotp, otp)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    finish()
                    this.startActivity(Intent(this, profileSetupActivity::class.java))
                } else {
                    Toast.makeText(applicationContext, "failed", Toast.LENGTH_SHORT).show()
                }
            }
    }


    @Composable
    fun authOtp(number: String) {
        var otp by remember { mutableStateOf("") }
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = Color.Transparent
            ) {
                Text(
                    text = "Verifying your number",
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF008268),
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = "Waiting to automatically detect an SMS sent to $number.Wrong number?",
                fontSize = 15.sp,
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Center
            )
            TextField(
                value = otp,
                onValueChange = { otp = it },
                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(160.dp),
                textStyle = TextStyle(fontSize = 20.sp, color = Color.Black, textAlign = TextAlign.Center),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF008268),
                    unfocusedIndicatorColor = Color(0xFF008268),
                    cursorColor = Color(0xFF008268),
                    textColor = Color(0xFF008268)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        SignInWithCred(otp = otp)
                    }
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Enter 6-digit code")
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
                    if (number.length == 6) { SignInWithCred(otp = otp) }
                } ,
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(Color(0xFF008268))
            ) {
                androidx.compose.material3.Text(text = "Continue", color = Color.White)
            }
        }
    }

}