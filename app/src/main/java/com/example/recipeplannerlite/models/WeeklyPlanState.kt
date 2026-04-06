package com.example.recipeplannerlite.models

data class WeeklyPlanState(
    val plan: Map<String, Recipe?> = mapOf(
        "Lunes" to null,
        "Martes" to null,
        "Miércoles" to null,
        "Jueves" to null,
        "Viernes" to null,
        "Sábado" to null,
        "Domingo" to null
    ),
    val shoppingList: List<ShoppingItem> = emptyList(),
    val availableRecipes: List<Recipe> = emptyList()
)