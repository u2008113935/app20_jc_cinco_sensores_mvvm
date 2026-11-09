package com.example.app20_jc_cinco_sensores_mvvm.viewmodel

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app20_jc_cinco_sensores_mvvm.model.data.repository.SensorRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.math.sqrt

data class AcelerometroUiState(
    val disponible: Boolean = false,
    val x: Float = 0f,
    val y: Float = 0f,
    val z: Float = 0f,
    val magnitud: Float = 0f,
    val agitando: Boolean = false
)

class AcelerometroViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SensorRepository(application)
    private val disponible = repository.estaDisponible(Sensor.TYPE_ACCELEROMETER)

    val uiState: StateFlow<AcelerometroUiState> = repository
        .lecturas(Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_UI) // lectura mas rapida
        .map { v ->
            val magnitud = sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2])     // raiz(x²+y²+z²)
            AcelerometroUiState(
                disponible = true,
                x = v[0], y = v[1], z = v[2],                                // 3 ejes
                magnitud = magnitud,
                agitando = magnitud > UMBRAL_AGITACION                       // reposo ≈ 9.8 (gravedad)
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AcelerometroUiState(disponible = disponible)
        )

    private companion object {
        const val UMBRAL_AGITACION = 15f                                     // ajustable en clase
    }
}