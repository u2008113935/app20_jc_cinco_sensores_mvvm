package com.example.app20_jc_cinco_sensores_mvvm.view.screens

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app20_jc_cinco_sensores_mvvm.viewmodel.VozViewModel

// El launcher va en la View (necesita Activity); el resultado se entrega al ViewModel
@Composable
fun VozScreen(
    modifier: Modifier = Modifier,
    viewModel: VozViewModel = viewModel()
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    // Abre el reconocedor de Google y recibe el resultado
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {               // el usuario hablo
            val texto = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull()                                      // primera opcion reconocida
            viewModel.onTextoReconocido(texto)
        } else {
            viewModel.onCancelado()
        }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Captura de voz", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-PE")    // espanol de Peru
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora...")
            }
            try {
                launcher.launch(intent)
            } catch (e: ActivityNotFoundException) {                 // sin reconocedor: no se cae
                viewModel.onNoDisponible()
            }
        }) {
            Text("Hablar")
        }

        Spacer(Modifier.height(16.dp))
        Text(
            text = estado.texto.ifBlank { "Presiona \"Hablar\" y di una frase" },
            style = MaterialTheme.typography.titleLarge
        )

        estado.mensaje?.let {                                        // solo si hay mensaje
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        if (estado.historial.isNotEmpty()) {                         // sobrevive a la rotacion
            Spacer(Modifier.height(24.dp))
            Text("Historial", style = MaterialTheme.typography.titleMedium)
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(estado.historial) { frase ->
                    Text("• $frase", modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}