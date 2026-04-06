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


    val availableFilters: List<String>
        get() = _state.value.recipes
            .flatMap { it.ingredients }
            .map { it.name.lowercase().trim().replaceFirstChar { it.uppercase() } }
            .distinct()
            .sorted()

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
            val matchesSearch = recipe.name.contains(stateValue.searchQuery, ignoreCase = true)
            val matchesFilter = if (stateValue.selectedFilters.isEmpty()) {
                true
            } else {
                recipe.ingredients.any { ingredient ->
                    stateValue.selectedFilters.any { filter ->
                        ingredient.name.equals(filter, ignoreCase = true)
                    }
                                }
            }
            matchesSearch && matchesFilter
        }

        _state.value = _state.value.copy(filteredRecipes = filtered)
    }
}