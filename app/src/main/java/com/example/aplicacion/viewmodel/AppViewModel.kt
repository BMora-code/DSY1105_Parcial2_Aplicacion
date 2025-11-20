package com.example.aplicacion.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.aplicacion.data.UserPreferencesRepository
import com.example.aplicacion.data.dataStore
import com.example.aplicacion.navigation.Screen
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel global para manejar el estado de la aplicación, como la sesión.
 */
class AppViewModel(private val repo: UserPreferencesRepository) : ViewModel() {

    // Estado de la sesión leído desde DataStore
    val sessionState: StateFlow<Pair<Boolean, String>> =
        repo.userFlow.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            Pair(false, "")
        )

    // Estado del personaje animado (color o "pixel" simulado)
    var characterVariantIndex by mutableStateOf(0)
        private set

    // Estado de la imagen capturada (Bitmap en memoria)
    var capturedImage by mutableStateOf<Bitmap?>(null)

    // Función para cambiar la variante (simula mantener la acción al regresar)
    fun nextCharacterVariant() {
        characterVariantIndex = (characterVariantIndex + 1) % 3
    }

    fun logout(navController: NavHostController) {
        viewModelScope.launch {
            repo.clearSession() // Cierra la sesión
            // Limpiamos también el estado en memoria al cerrar sesión
            capturedImage = null
            characterVariantIndex = 0
            
            navController.navigate(Screen.Login.route) {
                // Limpia la pila de navegación para evitar volver con el botón atrás
                popUpTo(0) { inclusive = true }
            }
        }
    }

    companion object {
        fun factory(context: Context): androidx.lifecycle.ViewModelProvider.Factory =
            object : androidx.lifecycle.ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AppViewModel(UserPreferencesRepository(context.dataStore)) as T
                }
            }
    }
}