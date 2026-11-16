package com.example.taskflow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskflow.data.repository.AuthRepository
import com.example.taskflow.data.repository.TaskRepository
import com.example.taskflow.ui.screens.LoginScreen
import com.example.taskflow.ui.screens.RegisterScreen
import com.example.taskflow.ui.screens.TaskScreen

@Composable
fun TaskFlowApp() {
    val navController = rememberNavController()
    val authRepository = remember { AuthRepository() }
    val taskRepository = remember { TaskRepository() }

    val startDestination = if (authRepository.currentUser != null) "tasks" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                authRepository = authRepository,
                onLoginSuccess = {
                    navController.navigate("tasks") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }
        composable("register") {
            RegisterScreen(
                authRepository = authRepository,
                onRegisterSuccess = {
                    navController.navigate("tasks") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("tasks") {
            TaskScreen(
                authRepository = authRepository,
                taskRepository = taskRepository,
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("tasks") { inclusive = true }
                    }
                }
            )
        }
    }
}