package com.example.simplemanager.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.simplemanager.data.UserPreferencesRepository
import com.example.simplemanager.data.dataStore
import com.example.simplemanager.navigation.Screen
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

    fun logout(navController: NavHostController) {
        viewModelScope.launch {
            repo.clearSession() // Cierra la sesión
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