package com.example.whatsfordinner

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

class RecipeVM : ViewModel(){

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())


    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()
    fun addRecipe(recipe: Recipe) {
        _recipes.value += recipe
    }

    fun getRecipe(id: String): Recipe? {
        return _recipes.value.find { it.id == id }
    }
}