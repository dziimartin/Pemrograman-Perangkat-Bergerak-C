package com.example.happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyBirthdayApp()
        }
    }
}

@Composable
fun HappyBirthdayApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFADD8E6) // background biru muda
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Happy Birthday!",
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Wishing you all the best!",
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
}