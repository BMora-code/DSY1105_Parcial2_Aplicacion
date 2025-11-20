package com.example.aplicacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.aplicacion.navigation.AppNavigation
import com.example.aplicacion.ui.theme.AplicacionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ¡IMPORTANTE! Asegúrate de añadir los siguientes permisos en tu archivo AndroidManifest.xml:
        // <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
        // <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
        // <uses-permission android:name="android.permission.CAMERA" />

        setContent {
            // Se asume que tienes un tema AplicacionTheme definido en ui.theme
            AplicacionTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Lanza el componente de navegación principal
                    AppNavigation()
                }
            }
        }
    }
}