package com.example.whatsfordinner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    vm: RecipeVM,
    onRecipeClick: (String) -> Unit
) {
    val recipes by vm.recipes.collectAsState()

    if (recipes.isEmpty()) {
        Text("No recipes found, add some with the button below!")
    } else {
        LazyColumn {
            items(recipes, key = { it.id }) { recipe ->
                Text(
                    text = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onRecipeClick(recipe.id) }
                        .padding(16.dp)
                )
            }
        }
    }
}
