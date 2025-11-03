package com.example.whatsfordinner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreen(vm: RecipeVM, recipeId: String) {
    val recipe = vm.getRecipe(recipeId)

    Scaffold { padding ->
        if (recipe == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text("Recipe not found")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text("Ingredients:", style = MaterialTheme.typography.titleMedium)
                recipe.ingredients.forEach { Text("• $it") }

                Text("Steps:", style = MaterialTheme.typography.titleMedium)
                recipe.steps.forEachIndexed { i, step ->
                    Text("${i + 1}. $step")
                }
            }
        }
    }
}
