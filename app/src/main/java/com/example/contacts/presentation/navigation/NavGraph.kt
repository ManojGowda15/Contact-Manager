package com.example.contacts.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.contacts.presentation.ContactViewModel
import com.example.contacts.presentation.screen.AddEditScreen
import com.example.contacts.presentation.screen.HomeScreen

@Composable
fun NavGraph(navHostController: NavHostController, viewModel: ContactViewModel) {
    val state = viewModel.state.collectAsState()

    NavHost(navController = navHostController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) {
            HomeScreen(
                navHostController = navHostController,
                state = state.value,
                viewModel = viewModel
            )
        }

        composable(Routes.AddEdit.route) {
            AddEditScreen(
                navHostController = navHostController,
                state = viewModel.state.collectAsState().value,
                onEvent = { viewModel.saveContact() }
            )
        }
    }
}