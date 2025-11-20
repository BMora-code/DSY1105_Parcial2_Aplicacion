package com.example.simplemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.simplemanager.navigation.AppNavigation
import com.example.simplemanager.ui.theme.SimpleManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ¡IMPORTANTE! Asegúrate de añadir los siguientes permisos en tu archivo AndroidManifest.xml:
        // <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
        // <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
        // <uses-permission android:name="android.permission.CAMERA" />

        setContent {
            // Se asume que tienes un tema SimpleManagerTheme definido en ui.theme
            SimpleManagerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Lanza el componente de navegación principal
                    AppNavigation()
                }
            }
        }
    }
}