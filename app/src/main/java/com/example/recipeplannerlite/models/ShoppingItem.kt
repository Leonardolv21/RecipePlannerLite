package com.example.recipeplannerlite.models

data class ShoppingItem(
    val name: String,
    val quantity: String,
    val isBought: Boolean = false
)