package com.example.recipeplannerlite.ViewModels

import androidx.lifecycle.ViewModel
import com.example.recipeplannerlite.models.CreateRecipeState
import com.example.recipeplannerlite.models.EditableIngredient
import com.example.recipeplannerlite.models.Ingredient
import com.example.recipeplannerlite.models.Recipe
import com.example.recipeplannerlite.models.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateRecipeViewModel : ViewModel() {
    private val _state = MutableStateFlow(
        CreateRecipeState(
            ingredients = listOf(
                EditableIngredient(id = 1)
            )
        )
    )
    val state: StateFlow<CreateRecipeState> = _state.asStateFlow()

    fun updateName(value: String) {
        _state.value = _state.value.copy(name = value)
    }

    fun updateDescription(value: String) {
        _state.value = _state.value.copy(description = value)
    }


    fun addIngredient() {
        val list = _state.value.ingredients.toMutableList()
        val newId = (list.maxOfOrNull { it.id } ?: 0) + 1

        list.add(EditableIngredient(id = newId))

        _state.value = _state.value.copy(ingredients = list)
    }

    fun removeIngredient(id: Int) {
        val updated = _state.value.ingredients.filter { it.id != id }
        _state.value = _state.value.copy(ingredients = updated)
    }

    fun updateIngredientName(id: Int, value: String) {
        val updated = _state.value.ingredients.map {
            if (it.id == id) it.copy(name = value) else it
        }
        _state.value = _state.value.copy(ingredients = updated)
    }

    fun updateIngredientQty(id: Int, value: String) {
        val updated = _state.value.ingredients.map {
            if (it.id == id) it.copy(quantity = value) else it
        }
        _state.value = _state.value.copy(ingredients = updated)
    }

    fun saveRecipe() {
        val state = _state.value

        val hasError = state.name.isBlank() ||
                state.ingredients.any { it.name.isBlank() || it.quantity.isBlank() }
        if (hasError) return

        val newRecipe = Recipe(
            id = System.currentTimeMillis().toInt(),
            name = state.name,
            description = state.description,
            ingredients = state.ingredients.map {
                Ingredient(it.name, it.quantity)
            }
        )
        RecipeRepository.addRecipe(newRecipe)

        _state.value = state.copy(isSaved = true)
    }
}