package com.example.radiofrance.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.radiofrance.presentation.Router
import com.example.radiofrancestation.presentation.theme.RadioFranceStationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RadioFranceStationTheme {
                Router()
            }
        }
    }
}