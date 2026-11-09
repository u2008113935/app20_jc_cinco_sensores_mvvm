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
import com.example.app20_jc_cinco_sensores_mvvm.viewmodel.LuzViewModel

// View: solo dibuja el estado, no conoce el SensorManager
@Composable
fun LuzScreen(
    modifier: Modifier = Modifier,
    viewModel: LuzViewModel = viewModel()                            // crea o reutiliza el ViewModel
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()   // observa solo si la app esta visible

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sensor de luz", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        if (estado.disponible) {
            Text("%.1f lux".format(estado.lux), style = MaterialTheme.typography.displayMedium)
            Spacer(Modifier.height(8.dp))
            Text(estado.descripcion)                                 // texto calculado en el ViewModel
        } else {
            Text("No hay sensor de luz en este dispositivo")
        }
    }
}