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

// Estado de la pantalla: todo lo que la View necesita mostrar
data class LuzUiState(
    val disponible: Boolean = false,
    val lux: Float = 0f,
    val descripcion: String = "Esperando lectura..."
)

// AndroidViewModel: da acceso al Context para crear el repositorio
class LuzViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SensorRepository(application)              // Model
    private val disponible = repository.estaDisponible(Sensor.TYPE_LIGHT)

    val uiState: StateFlow<LuzUiState> = repository.lecturas(Sensor.TYPE_LIGHT)
        .map { valores ->                                                // dato crudo -> UiState
            val lux = valores[0]
            LuzUiState(disponible = true, lux = lux, descripcion = describir(lux))
        }
        .stateIn(                                                        // Flow -> StateFlow
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),             // sin pantalla 5 s = sensor apagado
            initialValue = LuzUiState(disponible = disponible)
        )

    // Logica de negocio: va en el ViewModel, no en la View
    private fun describir(lux: Float): String = when {
        lux < 10f -> "Oscuro (sugerir modo oscuro)"
        lux < 500f -> "Interior iluminado"
        else -> "Exterior / luz intensa"
    }
}