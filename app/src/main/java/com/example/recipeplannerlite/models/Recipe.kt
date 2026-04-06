package com.example.recipeplannerlite.models

data class Recipe(
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<Ingredient>
)
data class Ingredient(
    val name: String,
    val quantity: String
)
