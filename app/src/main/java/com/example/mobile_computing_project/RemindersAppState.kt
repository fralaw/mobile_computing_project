package com.example.mobile_computing_project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class RemindersAppState(
    val navController : NavHostController
){
    fun navigateBack() {
        navController.popBackStack()
    }
}

@Composable
fun rememberRemindersAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController){
    RemindersAppState(navController)
}