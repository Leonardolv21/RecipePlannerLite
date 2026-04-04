package com.example.recipeplannerlite.models



data class RecipeState(
    val searchQuery: String = "",
    val selectedFilters: List<String> = emptyList(),
    val recipes: List<Recipe> = emptyList(),
    val filteredRecipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false
)
