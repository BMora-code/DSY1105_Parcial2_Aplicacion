package com.example.simplemanager.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Define las claves utilizadas para DataStore Preferences.
 */
object PrefsKeys {
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val USER_EMAIL = stringPreferencesKey("user_email")
}