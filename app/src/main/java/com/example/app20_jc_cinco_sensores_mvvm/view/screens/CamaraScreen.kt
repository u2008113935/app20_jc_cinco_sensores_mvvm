package com.example.app20_jc_cinco_sensores_mvvm.view.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app20_jc_cinco_sensores_mvvm.viewmodel.CamaraViewModel

// Flujo: boton -> verificar permiso -> pedir permiso -> abrir camara -> foto
@Composable
fun CamaraScreen(
    modifier: Modifier = Modifier,
    viewModel: CamaraViewModel = viewModel()
) {
    val context = LocalContext.current
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    // Launcher 1: camara del sistema, devuelve miniatura (no es CameraX)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap -> viewModel.onFotoTomada(bitmap) }

    // Launcher 2: dialogo de permiso "Permitir / No permitir"
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) cameraLauncher.launch(null) else viewModel.onPermisoDenegado()
    }

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),            // "modifier": respeta el Scaffold
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Camara", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            val yaConcedido = ContextCompat.checkSelfPermission(     // ya dio permiso antes?
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED

            if (yaConcedido) cameraLauncher.launch(null)
            else permissionLauncher.launch(Manifest.permission.CAMERA)
        }) {
            Text("Abrir camara")
        }

        estado.mensaje?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        estado.foto?.let { foto ->                                   // solo si hay foto
            Spacer(Modifier.height(16.dp))
            Image(
                bitmap = foto.asImageBitmap(),                       // Bitmap -> ImageBitmap (Compose)
                contentDescription = "Foto capturada",
                modifier = Modifier.size(300.dp)
            )
        }
    }
}