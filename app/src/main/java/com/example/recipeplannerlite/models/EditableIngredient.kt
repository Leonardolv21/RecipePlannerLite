package com.example.recipeplannerlite.models

data class EditableIngredient(
    val id: Int,
    val name: String = "",
    val quantity: String = "",
    val hasError: Boolean = false
)
