package com.example.kalkulatorsederhana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            kalkulatorsederhana()
        }
    }
}

@Composable
fun kalkulatorsederhana() {
    var persamaan by remember { mutableStateOf("") }
    var hasil by remember { mutableStateOf("0") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C1C)) // Warna background gelap
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = persamaan,
            fontSize = 32.sp,
            color = Color.LightGray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
        Text(
            text = hasil,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            lineHeight = 70.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        val buttons = listOf(
            listOf("AC", "DEL", "%", "/"),
            listOf("7", "8", "9", "*"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("0", ".", "=", "")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { label ->
                    if (label.isNotEmpty()) {
                        CalcButton(
                            label = label,
                            modifier = Modifier
                                .weight(if (label == "=") 2f else 1f)
                                .aspectRatio(if (label == "=") 2f else 1f),
                            onClick = {
                                when (label) {
                                    "AC" -> { persamaan = ""; hasil = "0" }
                                    "DEL" -> if (persamaan.isNotEmpty()) persamaan = persamaan.dropLast(1)
                                    "=" -> hasil = evaluasiPersamaan(persamaan)
                                    else -> persamaan += label
                                }
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CalcButton(label: String, modifier: Modifier, onClick: () -> Unit) {
    val isOperator = label in listOf("/", "*", "-", "+", "=", "AC", "DEL", "%")
    val bgColor = when {
        label == "=" -> Color(0xFFFF9800)
        isOperator -> Color(0xFF333333)
        else -> Color(0xFF505050)
    }

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = bgColor),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(text = label, fontSize = 24.sp, fontWeight = FontWeight.Medium, color = Color.White)
    }
}

fun evaluasiPersamaan(input: String): String {
    return try {
        val operators = listOf("+", "-", "*", "/", "%")
        val op = operators.find { input.contains(it) } ?: return input
        val parts = input.split(op)

        if (parts.size < 2) return input

        val d1 = parts[0].toDouble()
        val d2 = parts[1].toDouble()

        val res = when (op) {
            "+" -> d1 + d2
            "-" -> d1 - d2
            "*" -> d1 * d2
            "/" -> d1 / d2
            "%" -> d1 % d2
            else -> 0.0
        }

        if (res % 1 == 0.0) res.toInt().toString() else res.toString()
    } catch (e: Exception) {
        "Error"
    }
}