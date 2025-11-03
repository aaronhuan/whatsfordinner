package com.example.whatsfordinner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddScreen(
    vm: RecipeVM,
    onRecipeAdded: (String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var steps by remember { mutableStateOf("") }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Recipe Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("Ingredients (comma-separated)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = steps,
                onValueChange = { steps = it },
                label = { Text("Steps (comma-separated)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val ingredientsList = ingredients
                        .split(",")
                        .mapNotNull { s -> s.trim().ifEmpty { null } }
                    val stepsList = steps
                        .split(",")
                        .mapNotNull { s -> s.trim().ifEmpty { null } }

                    val newId = (vm.recipes.value.size + 1).toString()
                    val newRecipe = Recipe(
                        id = newId,
                        title = title.ifBlank { "Untitled" },
                        ingredients = ingredientsList,
                        steps = stepsList
                    )

                    vm.addRecipe(newRecipe)

                    // Navigate with backstack control handled in RecipeApp
                    onRecipeAdded(newId)
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Add Recipe") }
        }
    }
}
