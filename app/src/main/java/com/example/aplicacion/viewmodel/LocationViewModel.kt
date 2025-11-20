package com.example.aplicacion.viewmodel

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * Estado que contiene los datos de geolocalización.
 */
data class LocationState(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val status: String = "Esperando ubicación..."
)

/**
 * ViewModel para la pantalla de Ubicación.
 */
class LocationViewModel : ViewModel() {
    var locationState by mutableStateOf(LocationState())
        private set

    fun updateLocation(location: Location) {
        locationState = LocationState(
            latitude = location.latitude,
            longitude = location.longitude,
            status = "Ubicación obtenida con éxito"
        )
    }

    fun updateStatus(status: String) {
        locationState = locationState.copy(status = status)
    }
}