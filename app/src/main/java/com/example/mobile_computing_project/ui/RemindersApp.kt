package com.example.mobile_computing_project.ui

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mobile_computing_project.RemindersAppState
import com.example.mobile_computing_project.rememberRemindersAppState
import com.example.mobile_computing_project.ui.editReminder.EditReminder
import com.example.mobile_computing_project.ui.home.Home
import com.example.mobile_computing_project.ui.login.Login
import com.example.mobile_computing_project.ui.reminder.Reminder


@Composable
fun RemindersApp(
    sp: SharedPreferences,
    appState: RemindersAppState = rememberRemindersAppState(),
){
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ) {
        composable(route = "login")
        {
            Login(navController = appState.navController, sp)
        }
        composable(route = "home")
        {
            Home(navController = appState.navController)
        }
        composable(route = "reminder")
        {
            Reminder(onBackPress = appState::navigateBack)
        }
        composable(route = "editReminder/{reminder_id}",arguments = listOf(
            navArgument("reminder_id") {
                type = NavType.LongType
            }
        ))
        {
            backStackEntry ->
            val reminderId = backStackEntry.arguments?.getLong("reminder_id")
            EditReminder(onBackPress = appState::navigateBack, reminderId = reminderId)
        }
    }
}