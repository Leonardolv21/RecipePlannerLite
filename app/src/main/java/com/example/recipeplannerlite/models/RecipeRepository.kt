package com.example.recipeplannerlite.models

object RecipeRepository {
    private val recipes = mutableListOf(
        Recipe(
            id = 1,
            name = "Pasta Carbonara",
            description = "Clásica italiana",
            ingredients = listOf(
                Ingredient("Espagueti", "200g")
            )
        )
    )

    fun getRecipes(): List<Recipe> {
        return recipes
    }

    fun addRecipe(recipe: Recipe) {
        recipes.add(recipe)
    }

}