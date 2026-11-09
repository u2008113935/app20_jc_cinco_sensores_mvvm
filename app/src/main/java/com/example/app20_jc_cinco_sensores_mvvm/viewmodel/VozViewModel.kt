package com.example.app20_jc_cinco_sensores_mvvm.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class VozUiState(
    val texto: String = "",
    val historial: List<String> = emptyList(),
    val mensaje: String? = null                                      // null = sin aviso
)

// ViewModel simple (sin Context). Patron de EVENTOS: la View avisa, el ViewModel actualiza
class VozViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(VozUiState())            // privado: se modifica aqui
    val uiState: StateFlow<VozUiState> = _uiState.asStateFlow()      // publico: solo lectura

    fun onTextoReconocido(texto: String?) {
        if (texto.isNullOrBlank()) {
            _uiState.update { it.copy(mensaje = "No se reconocio ninguna frase") }
            return
        }
        _uiState.update {                                            // copy = nuevo estado inmutable
            it.copy(texto = texto, historial = listOf(texto) + it.historial, mensaje = null)
        }
    }

    fun onCancelado() {                                              // cerro el dialogo sin hablar
        _uiState.update { it.copy(mensaje = "Reconocimiento cancelado") }
    }

    fun onNoDisponible() {                                           // celular sin reconocedor
        _uiState.update {
            it.copy(mensaje = "Este dispositivo no tiene servicio de reconocimiento de voz")
        }
    }
}