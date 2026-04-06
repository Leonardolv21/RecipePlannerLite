package com.example.recipeplannerlite.Navegation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipeplannerlite.ViewModels.WeeklyPlanViewModel
import com.example.recipeplannerlite.views.CreateRecipeScreen
import com.example.recipeplannerlite.views.RecipeListScreen
import com.example.recipeplannerlite.views.ShoppingListScreen
import com.example.recipeplannerlite.views.WeeklyPlanScreen

sealed class Screen(val route: String) {
    object RecipeList : Screen("list")
    object CreateRecipe : Screen("create")
    object WeeklyPlan : Screen("weekly")
    object ShoppingList : Screen("shopping")
}

@Composable
fun AppNavigation(innerPadding: PaddingValues) {
    val navController = rememberNavController()
    val weeklyPlanViewModel: WeeklyPlanViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.RecipeList.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.RecipeList.route) {
            RecipeListScreen(
                onNavigateToCreate = {
                    navController.navigate(Screen.CreateRecipe.route)
                },
                onNavigateToWeekly = {
                    navController.navigate(Screen.WeeklyPlan.route)
                }
            )
        }
        composable(Screen.CreateRecipe.route) {
            CreateRecipeScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.WeeklyPlan.route) {
            WeeklyPlanScreen(
                vm = weeklyPlanViewModel,
                onNavigateToShopping = {
                    navController.navigate(Screen.ShoppingList.route)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.ShoppingList.route) {
            ShoppingListScreen(
                vm = weeklyPlanViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}