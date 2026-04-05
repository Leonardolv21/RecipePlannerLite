package com.example.recipeplannerlite.Navegation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipeplannerlite.views.CreateRecipeScreen
import com.example.recipeplannerlite.views.RecipeListScreen


sealed class Screen (val route: String) {
    object RecipeList : Screen("list")
    object CreateRecipe : Screen("create")
}
@Composable
fun AppNavigation(innerPadding: PaddingValues){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.RecipeList.route,
        modifier = Modifier.padding(innerPadding)
    ){
        composable(Screen.RecipeList.route) {
            RecipeListScreen(
                onNavigateToCreate = {
                    navController.navigate(Screen.CreateRecipe.route)
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
    }
}