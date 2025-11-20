package com.example.simplemanager.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplemanager.data.UserPreferencesRepository
import com.example.simplemanager.data.dataStore
import com.example.simplemanager.utils.ValidationState
import com.example.simplemanager.utils.Validators
import kotlinx.coroutines.launch

/**
 * ViewModel para las pantallas de Login y Registro.
 */
class AuthViewModel(
    private val repo: UserPreferencesRepository,
    context: Context
) : ViewModel() {

    // Estados mutables para los campos del formulario
    var emailState by mutableStateOf(ValidationState())
    var passwordState by mutableStateOf(ValidationState())
    var registrationMessage by mutableStateOf<String?>(null)

    // Propiedad calculada para habilitar el botón "Ingresar"
    val isLoginValid: Boolean
        get() = emailState.error == null && passwordState.error == null &&
                emailState.value.isNotEmpty() && passwordState.value.isNotEmpty()

    // Manejadores de cambios para actualizar el estado y validar en tiempo real
    fun onEmailChange(newEmail: String) {
        emailState = emailState.copy(
            value = newEmail,
            error = Validators.validateEmail(newEmail)
        )
    }

    fun onPasswordChange(newPassword: String) {
        passwordState = passwordState.copy(
            value = newPassword,
            error = Validators.validatePassword(newPassword)
        )
    }

    fun register(passwordConfirmation: String, onSuccess: () -> Unit) {
        // Lógica de registro (simulada)
        if (passwordState.error == null && emailState.error == null &&
            passwordConfirmation == passwordState.value
        ) {
            registrationMessage = "Registro exitoso. ¡Inicia sesión!"
            onSuccess()
        } else {
            registrationMessage = "Error en el registro. Verifica los campos."
        }
    }

    fun login(onSuccess: () -> Unit) {
        if (isLoginValid) {
            viewModelScope.launch {
                repo.saveLoginState(emailState.value) // Guardar sesión
                onSuccess()
            }
        }
    }

    // Factory para la inyección de dependencias
    companion object {
        fun factory(context: Context): androidx.lifecycle.ViewModelProvider.Factory =
            object : androidx.lifecycle.ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AuthViewModel(
                        UserPreferencesRepository(context.dataStore),
                        context
                    ) as T
                }
            }
    }
}