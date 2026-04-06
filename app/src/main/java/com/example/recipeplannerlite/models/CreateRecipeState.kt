package com.example.recipeplannerlite.models

data class CreateRecipeState(
    val name: String = "",
    val description: String = "",
    val ingredients: List<EditableIngredient> = emptyList(),
    val showNameError: Boolean = false,
    val isSaved: Boolean = false
)