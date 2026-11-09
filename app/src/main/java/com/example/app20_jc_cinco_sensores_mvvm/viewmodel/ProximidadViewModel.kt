package com.example.app20_jc_cinco_sensores_mvvm.viewmodel

import android.app.Application
import android.hardware.Sensor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app20_jc_cinco_sensores_mvvm.model.data.repository.SensorRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ProximidadUiState(
    val disponible: Boolean = false,
    val distancia: Float = 0f,
    val rangoMaximo: Float = 0f,
    val cerca: Boolean = false
)

// Mismo patron que Luz: solo cambia el tipo de sensor y la logica
class ProximidadViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SensorRepository(application)
    private val disponible = repository.estaDisponible(Sensor.TYPE_PROXIMITY)
    private val rangoMaximo = repository.rangoMaximo(Sensor.TYPE_PROXIMITY)  // ej. 5 cm

    val uiState: StateFlow<ProximidadUiState> = repository.lecturas(Sensor.TYPE_PROXIMITY)
        .map { valores ->
            val distancia = valores[0]
            ProximidadUiState(
                disponible = true,
                distancia = distancia,
                rangoMaximo = rangoMaximo,
                cerca = distancia < rangoMaximo                          // sensor binario: 0 = cerca
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProximidadUiState(disponible = disponible, rangoMaximo = rangoMaximo)
        )
}