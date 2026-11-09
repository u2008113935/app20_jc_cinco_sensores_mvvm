package com.example.app20_jc_cinco_sensores_mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.app20_jc_cinco_sensores_mvvm.view.MainScreen
import com.example.app20_jc_cinco_sensores_mvvm.view.theme.App20_jc_cinco_sensores_mvvmTheme

// Punto de entrada: solo carga el tema y la MainScreen (sin logica)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()                                           // pantalla completa
        setContent {
            App20_jc_cinco_sensores_mvvmTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding)) // evita barras del sistema
                }
            }
        }
    }
}