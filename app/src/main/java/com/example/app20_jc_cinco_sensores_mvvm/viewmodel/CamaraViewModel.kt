package com.example.app20_jc_cinco_sensores_mvvm.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CamaraUiState(
    val foto: Bitmap? = null,                                        // null = sin foto
    val mensaje: String? = null
)

// Mismo patron de eventos que Voz
class CamaraViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CamaraUiState())
    val uiState: StateFlow<CamaraUiState> = _uiState.asStateFlow()

    // La foto vive aqui: no se pierde al girar el telefono
    fun onFotoTomada(bitmap: Bitmap?) {
        if (bitmap != null) {
            _uiState.update { it.copy(foto = bitmap, mensaje = null) }
        } else {
            _uiState.update { it.copy(mensaje = "Captura cancelada") }
        }
    }

    fun onPermisoDenegado() {                                        // el usuario nego el permiso
        _uiState.update { it.copy(mensaje = "Se necesita el permiso de camara para tomar fotos") }
    }
}