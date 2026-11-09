package com.example.app20_jc_cinco_sensores_mvvm.model.data.repository

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow


class SensorRepository (context: Context) {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    fun estaDisponible(tipo: Int): Boolean =
        sensorManager.getDefaultSensor(tipo) != null

    fun rangoMaximo(tipo: Int): Float =
        sensorManager.getDefaultSensor(tipo)?.maximumRange ?: 0f

    fun lecturas(
        tipo: Int,
        periodo: Int = SensorManager.SENSOR_DELAY_NORMAL
    ): Flow<FloatArray> {

        val sensor = sensorManager.getDefaultSensor(tipo) ?: return emptyFlow()

            return callbackFlow {
                val listener = object : SensorEventListener {
                    override fun onSensorChanged(event: SensorEvent) {
                        trySend(event.values.clone())
                    }

                    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
                    }

                sensorManager.registerListener(listener, sensor, periodo)

                awaitClose { sensorManager.unregisterListener(listener)}

                }
            }
}