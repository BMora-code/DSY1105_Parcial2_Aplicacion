package com.example.simplemanager.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.simplemanager.navigation.Screen
import com.example.simplemanager.ui.components.ValidatedTextField
import com.example.simplemanager.viewmodel.AuthViewModel

/**
 * Pantalla de Login.
 */
@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.factory(context))

    // Animación de entrada en pantallas
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(initialOffsetY = { -40 }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -40 }) + fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Bienvenido", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(32.dp))

            // Campo de Email
            ValidatedTextField(
                state = authViewModel.emailState,
                label = "Correo Electrónico",
                icon = Icons.Default.Email,
                onValueChange = authViewModel::onEmailChange
            )
            Spacer(Modifier.height(16.dp))

            // Campo de Contraseña
            ValidatedTextField(
                state = authViewModel.passwordState,
                label = "Contraseña",
                icon = Icons.Default.Lock,
                onValueChange = authViewModel::onPasswordChange,
                isPassword = true
            )
            Spacer(Modifier.height(32.dp))

            // Botón "Ingresar" habilitado solo si los campos son válidos
            Button(
                onClick = {
                    authViewModel.login(onSuccess = {
                        Toast.makeText(context, "¡Login exitoso!", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    })
                },
                enabled = authViewModel.isLoginValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ingresar")
            }
            Spacer(Modifier.height(16.dp))

            // Botón "Ir a Registro"
            TextButton(onClick = { navController.navigate(Screen.Register.route) }) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }
    }
}