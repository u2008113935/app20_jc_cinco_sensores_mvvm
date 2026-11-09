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
import com.example.app20_jc_cinco_sensores_mvvm.viewmodel.ProximidadViewModel

// Misma estructura que LuzScreen
@Composable
fun ProximidadScreen(
    modifier: Modifier = Modifier,
    viewModel: ProximidadViewModel = viewModel()
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sensor de proximidad", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        if (estado.disponible) {
            Text(
                text = if (estado.cerca) "CERCA" else "LEJOS",
                style = MaterialTheme.typography.displayMedium,
                color = if (estado.cerca) MaterialTheme.colorScheme.error   // rojo = cerca
                else MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            Text("Lectura: ${estado.distancia} cm (rango maximo: ${estado.rangoMaximo} cm)")
            Text(
                "Acerca la mano a la parte superior del telefono",          // sensor junto al auricular
                style = MaterialTheme.typography.bodySmall
            )
        } else {
            Text("No hay sensor de proximidad en este dispositivo")
        }
    }
}