// app/src/main/java/com/example/whatsfordinner/RecipeApp.kt
package com.example.whatsfordinner

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

sealed class Routes(val route: String) {
    data object Home : Routes("home")
    data object Add : Routes("add")
    data object Settings : Routes("settings")
    data object Detail : Routes("detail/{recipeId}") {
        const val ARG = "recipeId" // Name of the argument
        fun create(recipeId: String) = "detail/$recipeId"// Helper to build actual route
    }
}

@Composable
fun RecipeApp(vm: RecipeVM) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNav(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Home
            composable(Routes.Home.route) {
                HomeScreen(
                    vm = vm,
                    onRecipeClick = { id ->
                        // Pass argument via route + "/{id}"
                        navController.navigate(Routes.Detail.create(id)) {
                            launchSingleTop = true // prevent stacking detail
                        }
                    }
                )
            }

            composable(Routes.Add.route) {
                AddScreen(
                    vm = vm,
                    onRecipeAdded = {//helper function to call when a recipe is called
                        navController.navigate(Routes.Home.route) { // go to the home screen
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            composable(Routes.Settings.route) {
                SettingsScreen()
            }

            composable(
                route = Routes.Detail.route,
                arguments = listOf(
                    navArgument(Routes.Detail.ARG) { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val id = requireNotNull(backStackEntry.arguments?.getString(Routes.Detail.ARG))
                DetailScreen(vm = vm, recipeId = id)
            }
        }
    }
}

@Composable
private fun BottomNav(navController: NavHostController) {
    val items = listOf(Routes.Home, Routes.Add, Routes.Settings)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { route ->
            // Highlight Home when on a detail page
            val selected = when (route) { //if the route is selected
                is Routes.Home ->
                    currentRoute == Routes.Home.route || (currentRoute?.startsWith("detail/") == true)
                else -> currentRoute == route.route
            }

            NavigationBarItem(
                selected = selected,
                onClick = {
                    when (route) {
                        is Routes.Home -> {
                            navController.navigate(Routes.Home.route) {
                                popUpTo(Routes.Home.route) { inclusive = true } // remove everything including home
                                launchSingleTop = true//prevent dupes
                            }
                        }
                        else -> {
                            navController.navigate(route.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true //save the previous UI state of the destination
                                }
                                launchSingleTop = true
                                restoreState = true //restores previous state of destination  ( remebers it like what u types/scrolled to)
                            }
                        }
                    }
                },
                icon = { },
                label = {
                    Text(
                        when (route) {
                            is Routes.Home -> "Home"
                            is Routes.Add -> "Add"
                            is Routes.Settings -> "Settings"
                            is Routes.Detail -> "Detail"
                        }
                    )
                }
            )
        }
    }
}
