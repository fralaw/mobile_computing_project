package com.example.mobile_computing_project.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobile_computing_project.RemindersAppState
import com.example.mobile_computing_project.rememberRemindersAppState
import com.example.mobile_computing_project.ui.home.Home
import com.example.mobile_computing_project.ui.login.Login


@Composable
fun RemindersApp(
    appState : RemindersAppState = rememberRemindersAppState()
){
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ) {
        composable(route = "login")
        {
            Login(navController = appState.navController)
        }
        composable(route = "home")
        {
            Home(navController = appState.navController)
        }

    }
}