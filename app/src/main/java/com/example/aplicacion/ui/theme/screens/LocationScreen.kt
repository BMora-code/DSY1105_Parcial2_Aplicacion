package com.example.aplicacion.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.aplicacion.viewmodel.LocationViewModel

/**
 * Pantalla de Ubicación, solicita permisos de GPS (Recurso Nativo 1).
 */
@SuppressLint("MissingPermission")
@Composable
fun LocationScreen(navController: NavHostController) {
    val locationViewModel: LocationViewModel = viewModel()
    val context = LocalContext.current
    val state = locationViewModel.locationState
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    // 1. Solicitud de Permisos
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true -> {
                locationViewModel.updateStatus("Permiso concedido. Obteniendo ubicación...")
            }
            else -> {
                locationViewModel.updateStatus("Permiso de Ubicación Denegado.")
                Toast.makeText(context, "Se necesita permiso de GPS para ver la ubicación", Toast.LENGTH_LONG).show()
            }
        }
    }

    // 2. Lógica de obtención de ubicación
    DisposableEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
        } else {
            val lastLocation: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (lastLocation != null) {
                locationViewModel.updateLocation(lastLocation)
            } else {
                locationViewModel.updateStatus("No se pudo obtener la última ubicación conocida. Intente de nuevo.")
            }
        }

        onDispose { /* Cleanup, si fuera necesario */ }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mi Ubicación Actual", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Estado del GPS:", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text(state.status, color = if (state.latitude != null) Color.Green.copy(alpha = 0.8f) else Color.Red.copy(alpha = 0.8f))
                Spacer(Modifier.height(16.dp))

                // Mostrar latitud y longitud
                Text("Latitud:", style = MaterialTheme.typography.bodyLarge)
                Text(state.latitude?.toString() ?: "N/A", style = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray))
                Spacer(Modifier.height(8.dp))
                Text("Longitud:", style = MaterialTheme.typography.bodyLarge)
                Text(state.longitude?.toString() ?: "N/A", style = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray))
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
            Text("Volver a Home")
        }
    }
}