package com.example.aplicacion.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// Configuración global de DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

/**
 * Repositorio encargado de leer y escribir la información de sesión en DataStore.
 */
class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    // Flujo para leer el estado de la sesión y el email
    val userFlow: Flow<Pair<Boolean, String>> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val isLoggedIn = preferences[PrefsKeys.IS_LOGGED_IN] ?: false
            val email = preferences[PrefsKeys.USER_EMAIL] ?: ""
            Pair(isLoggedIn, email)
        }

    /**
     * Guarda el estado de la sesión como activa y el email del usuario.
     */
    suspend fun saveLoginState(email: String) {
        dataStore.edit { preferences ->
            preferences[PrefsKeys.IS_LOGGED_IN] = true
            preferences[PrefsKeys.USER_EMAIL] = email
        }
    }

    /**
     * Cierra la sesión borrando todos los datos de DataStore.
     */
    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}