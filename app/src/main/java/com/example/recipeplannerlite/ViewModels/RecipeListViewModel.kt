package com.example.recipeplannerlite.ViewModels

import androidx.lifecycle.ViewModel
import com.example.recipeplannerlite.models.RecipeRepository
import com.example.recipeplannerlite.models.RecipeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.filter

class RecipeListViewModel : ViewModel() {
    private val _state = MutableStateFlow(RecipeState())
    val state: StateFlow<RecipeState> = _state.asStateFlow()

    init {
        loadRecipes()
    }
    fun reloadRecipes() {
        val recipes = RecipeRepository.getRecipes()

        _state.value = _state.value.copy(
            recipes = recipes,
            filteredRecipes = recipes
        )
    }
    private fun loadRecipes() {
        val recipes = RecipeRepository.getRecipes()

        _state.value = _state.value.copy(
            recipes = recipes,
            filteredRecipes = recipes
        )
    }

    fun updateSearch(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        filterRecipes()
    }

    fun toggleFilter(filter: String) {
        val current = _state.value.selectedFilters.toMutableList()

        if (current.contains(filter)) {
            current.remove(filter)
        } else {
            current.add(filter)
        }

        _state.value = _state.value.copy(selectedFilters = current)
        filterRecipes()
    }

    private fun filterRecipes() {
        val stateValue = _state.value

        val filtered = stateValue.recipes.filter { recipe ->
            recipe.name.contains(stateValue.searchQuery, ignoreCase = true) &&
                    (
                            stateValue.selectedFilters.isEmpty() ||
                                    stateValue.selectedFilters.any { filter ->
                                        recipe.ingredients.any { ingredient ->
                                            ingredient.name.equals(filter, ignoreCase = true)
                                        }
                                    }
                            )
        }

        _state.value = _state.value.copy(filteredRecipes = filtered)
    }
}