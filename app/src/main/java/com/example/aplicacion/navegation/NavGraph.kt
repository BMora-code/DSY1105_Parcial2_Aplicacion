package com.example.simplemanager.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simplemanager.ui.screens.*
import com.example.simplemanager.viewmodel.AppViewModel

/**
 * Componente principal que maneja la navegación y el guard de sesión.
 */
@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val appViewModel: AppViewModel = viewModel(factory = AppViewModel.factory(context))

    // Leer el estado de la sesión para decidir la ruta inicial
    val session by appViewModel.sessionState.collectAsState()
    val startDestination = if (session.first) Screen.Home.route else Screen.Login.route

    // Uso de AnimatedContent para transiciones de pantalla
    AnimatedContent(
        targetState = startDestination,
        label = "Start Screen Transition",
        transitionSpec = {
            fadeIn(tween(700)) + slideInVertically(initialOffsetY = { it / 2 }, animationSpec = tween(700)) togetherWith
                    fadeOut(tween(700)) + slideOutVertically(targetOffsetY = { -it / 2 }, animationSpec = tween(700))
        }
    ) { currentStartDestination ->
        NavHost(
            navController = navController,
            startDestination = currentStartDestination
        ) {
            // Rutas principales
            composable(Screen.Login.route) { LoginScreen(navController) }
            composable(Screen.Register.route) { RegisterScreen(navController) }
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Profile.route) { ProfileScreen(navController) }
            composable(Screen.Location.route) { LocationScreen(navController) }
        }
    }
}
