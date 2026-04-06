package com.example.recipeplannerlite.ViewModels

import androidx.lifecycle.ViewModel
import com.example.recipeplannerlite.models.RecipeRepository
import com.example.recipeplannerlite.models.ShoppingItem
import com.example.recipeplannerlite.models.WeeklyPlanState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WeeklyPlanViewModel : ViewModel() {

    private val _state = MutableStateFlow(WeeklyPlanState())
    val state: StateFlow<WeeklyPlanState> = _state.asStateFlow()

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        _state.value = _state.value.copy(
            availableRecipes = RecipeRepository.getRecipes()
        )
    }

    fun reloadRecipes() {
        _state.value = _state.value.copy(
            availableRecipes = RecipeRepository.getRecipes()
        )
    }

    fun assignRecipeToDay(day: String, recipeId: Int) {
        val recipe = _state.value.availableRecipes.find { it.id == recipeId }
        val newPlan = _state.value.plan.toMutableMap()
        newPlan[day] = recipe
        _state.value = _state.value.copy(plan = newPlan)
        generateShoppingList()
    }

    private fun generateShoppingList() {
        val allIngredients = mutableMapOf<String, Pair<Int, String>>()

        _state.value.plan.values
            .filterNotNull()
            .forEach { recipe ->
                recipe.ingredients.forEach { ingredient ->
                    val key = ingredient.name.lowercase().trim()
                    val number = ingredient.quantity.filter { it.isDigit() }.toIntOrNull() ?: 1
                    val unit = ingredient.quantity.filter { it.isLetter() }.trim()

                    val existing = allIngredients[key]
                    if (existing != null) {
                        allIngredients[key] = Pair(existing.first + number, existing.second.ifBlank { unit })
                    } else {
                        allIngredients[key] = Pair(number, unit)
                    }
                }
            }

        val items = allIngredients.map { (name, pair) ->
            val existing = _state.value.shoppingList.find { it.name.lowercase().trim() == name }
            ShoppingItem(
                name = name,
                quantity = "${pair.first}${pair.second}",
                isBought = existing?.isBought ?: false
            )
        }

        _state.value = _state.value.copy(shoppingList = items)
    }
    fun toggleIngredientBought(name: String) {
        val updated = _state.value.shoppingList.map { item ->
            if (item.name == name) item.copy(isBought = !item.isBought)
            else item
        }
        _state.value = _state.value.copy(shoppingList = updated)
    }
}