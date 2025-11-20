package com.example.simplemanager.ui.screens

import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.simplemanager.viewmodel.AppViewModel

/**
 * Pantalla de Perfil. Integra el acceso a la Cámara (Recurso Nativo 2).
 */
@Composable
fun ProfileScreen(navController: NavHostController) {
    val appViewModel: AppViewModel = viewModel(factory = AppViewModel.factory(LocalContext.current))
    val session by appViewModel.sessionState.collectAsState()
    val userEmail = session.second
    val context = LocalContext.current
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }

    // Launcher para la cámara (Recurso Nativo 2)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        capturedImage = bitmap
        if (bitmap != null) {
            Toast.makeText(context, "Foto de perfil capturada", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mi Perfil", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(32.dp))

        // Foto de Perfil (Recurso Nativo 2: Cámara)
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .clickable { cameraLauncher.launch(null) } // Abre la cámara
        ) {
            if (capturedImage != null) {
                Image(
                    bitmap = capturedImage!!.asImageBitmap(),
                    contentDescription = "Foto de Perfil",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = "Placeholder",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.LightGray
                )
                Text(
                    text = "Tomar Foto",
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        // Mostrar email guardado
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Información de Usuario", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Mail, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Email Guardado:", style = MaterialTheme.typography.bodyLarge)
                }
                Text(userEmail, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 32.dp))

                // Mostrar nombre si corresponde (simulado)
                Spacer(Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Nombre (Simulado):", style = MaterialTheme.typography.bodyLarge)
                }
                Text("Usuario Demo", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 32.dp))
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
            Text("Volver a Home")
        }
    }
}