package com.example.recipeplannerlite.models

object RecipeRepository {
    fun getRecipes(): List<Recipe> {
        return listOf(
            Recipe(
                id = 1,
                name = "Pasta Carbonara",
                description = "Clásica italiana cremosa",
                emoji = "🍝",
                ingredients = listOf(
                    Ingredient("Espagueti", "200 g"),
                    Ingredient("Panceta", "100 g"),
                    Ingredient("Huevos", "2 unidades"),
                    Ingredient("Queso Parmesano", "50 g")
                )
            ),
            Recipe(
                id = 2,
                name = "Ensalada César",
                description = "Fresca y ligera",
                emoji = "🥗",
                ingredients = listOf(
                    Ingredient("Lechuga romana", "1 cabeza"),
                    Ingredient("Pollo", "150 g"),
                    Ingredient("Aderezo César", "3 cdas"),
                    Ingredient("Crutones", "1/2 taza")
                )
            ),
            Recipe(
                id = 3,
                name = "Tacos de Carne",
                description = "Sabor mexicano auténtico",
                emoji = "🌮",
                ingredients = listOf(
                    Ingredient("Tortillas", "6 unidades"),
                    Ingredient("Carne molida", "300 g"),
                    Ingredient("Cebolla", "1/2 unidad")
                )
            )
        )
    }
}