package com.example.simplemanager.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.simplemanager.navigation.Screen
import com.example.simplemanager.ui.components.ValidatedTextField
import com.example.simplemanager.utils.ValidationState
import com.example.simplemanager.viewmodel.AuthViewModel

/**
 * Pantalla de Registro.
 */
@OptIn(ExperimentalMaterial3Api::class) // Se agrega para manejar la advertencia de API experimental
@Composable
fun RegisterScreen(navController: NavHostController) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.factory(context))
    var confirmPasswordState by remember { mutableStateOf(ValidationState()) }

    // CORRECCIÓN: Se usa 'val' y se asigna el valor booleano directamente para que esté inicializada.
    val arePasswordsMatching: Boolean =
        authViewModel.passwordState.value == confirmPasswordState.value && confirmPasswordState.value.isNotEmpty()

    // CORRECCIÓN: Se usa 'val' y se asigna el valor booleano directamente.
    val isFormValid: Boolean =
        authViewModel.isLoginValid && arePasswordsMatching

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Crear Cuenta", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
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
        Spacer(Modifier.height(16.dp))

        // Campo de Confirmación de Contraseña
        ValidatedTextField(
            state = confirmPasswordState,
            label = "Confirmar Contraseña",
            icon = Icons.Default.Lock,
            onValueChange = {
                confirmPasswordState = confirmPasswordState.copy(value = it)
                // Usamos validación manual aquí para verificar la coincidencia
                if (it != authViewModel.passwordState.value) {
                    confirmPasswordState = confirmPasswordState.copy(error = "Las contraseñas no coinciden")
                } else {
                    confirmPasswordState = confirmPasswordState.copy(error = null)
                }
            },
            isPassword = true
        )
        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                authViewModel.register(
                    passwordConfirmation = confirmPasswordState.value,
                    onSuccess = {
                        Toast.makeText(context, authViewModel.registrationMessage, Toast.LENGTH_LONG).show()
                        navController.navigate(Screen.Login.route) // 'Screen' ahora está correctamente referenciado
                    }
                )
            },
            enabled = isFormValid, // 'isFormValid' ahora está inicializado
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }
        Spacer(Modifier.height(16.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Volver a Login")
        }
    }
}