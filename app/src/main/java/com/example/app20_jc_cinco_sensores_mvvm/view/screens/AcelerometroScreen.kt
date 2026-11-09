package com.example.app20_jc_cinco_sensores_mvvm.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app20_jc_cinco_sensores_mvvm.viewmodel.AcelerometroViewModel

@Composable
fun AcelerometroScreen(
    modifier: Modifier = Modifier,
    viewModel: AcelerometroViewModel = viewModel()
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Acelerometro (m/s²)", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        if (estado.disponible) {
            Text("X: %.2f".format(estado.x), style = MaterialTheme.typography.titleLarge) // 2 decimales
            Text("Y: %.2f".format(estado.y), style = MaterialTheme.typography.titleLarge)
            Text("Z: %.2f".format(estado.z), style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Text("Magnitud: %.2f".format(estado.magnitud))
            Spacer(Modifier.height(16.dp))
            Text(
                text = if (estado.agitando) "¡Agitacion detectada!" else "En reposo", // decide el ViewModel
                style = MaterialTheme.typography.titleMedium,
                color = if (estado.agitando) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onSurface
            )
        } else {
            Text("No hay acelerometro en este dispositivo")
        }
    }
}