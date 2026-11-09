package com.example.app20_jc_cinco_sensores_mvvm.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.app20_jc_cinco_sensores_mvvm.view.screens.AcelerometroScreen
import com.example.app20_jc_cinco_sensores_mvvm.view.screens.CamaraScreen
import com.example.app20_jc_cinco_sensores_mvvm.view.screens.LuzScreen
import com.example.app20_jc_cinco_sensores_mvvm.view.screens.ProximidadScreen
import com.example.app20_jc_cinco_sensores_mvvm.view.screens.VozScreen

private val pestanas = listOf("Voz", "Luz", "Proximidad", "Acelerometro", "Camara")

// Pantalla principal: navegacion por pestanas
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var seleccion by rememberSaveable { mutableIntStateOf(0) }       // se conserva al girar

    Column(modifier = modifier.fillMaxSize()) {
        PrimaryScrollableTabRow(selectedTabIndex = seleccion) {      // pestanas desplazables
            pestanas.forEachIndexed { indice, titulo ->
                Tab(
                    selected = seleccion == indice,
                    onClick = { seleccion = indice },
                    text = { Text(titulo) }
                )
            }
        }

        // Solo se dibuja la pantalla elegida; las demas liberan su sensor
        when (seleccion) {
            0 -> VozScreen()
            1 -> LuzScreen()
            2 -> ProximidadScreen()
            3 -> AcelerometroScreen()
            4 -> CamaraScreen()
        }
    }
}