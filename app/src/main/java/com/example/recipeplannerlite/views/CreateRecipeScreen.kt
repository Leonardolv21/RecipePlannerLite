package com.example.recipeplannerlite.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeplannerlite.ViewModels.CreateRecipeViewModel
import com.example.recipeplannerlite.models.EditableIngredient

@Composable
fun CreateRecipeScreen(
    vm: CreateRecipeViewModel = viewModel()
){
    val state = vm.state.collectAsState().value
    Column {
        TopBarCreate()

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            item {
                NameSection(
                    name = state.name,
                    emoji = state.selectedEmoji,
                    onNameChange = vm::updateName,
                    onEmojiSelect = vm::selectEmoji
                )
            }

            item {
                DescriptionSection(
                    value = state.description,
                    onChange = vm::updateDescription
                )
            }

            item {
                IngredientHeader(state.ingredients.size)
            }

            items(state.ingredients) { ingredient ->
                IngredientItemEditable(
                    ingredient = ingredient,
                    onNameChange = vm::updateIngredientName,
                    onQtyChange = vm::updateIngredientQty,
                    onDelete = vm::removeIngredient
                )
            }

            item {
                AddIngredientButton(vm::addIngredient)
            }
        }

        SaveButton(vm::saveRecipe)
    }
}

@Composable
fun IngredientHeader(x0: Int) {

}

@Composable
fun DescriptionSection(
    value: String,
    onChange: (String) -> Unit
) {

}

@Composable
fun NameSection(
    name: String,
    emoji: String,
    onNameChange: (String) -> Unit,
    onEmojiSelect: (String) -> Unit
) {

}

@Composable
fun TopBarCreate() {

}

@Composable
fun IngredientItemEditable(
    ingredient: EditableIngredient,
    onNameChange: (Int, String) -> Unit,
    onQtyChange: (Int, String) -> Unit,
    onDelete: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {

        Text("${ingredient.id}")

        TextField(
            value = ingredient.name,
            onValueChange = { onNameChange(ingredient.id, it) },
            modifier = Modifier.weight(1f)
        )

        TextField(
            value = ingredient.quantity,
            onValueChange = { onQtyChange(ingredient.id, it) },
            modifier = Modifier.width(80.dp)
        )

        IconButton(onClick = { onDelete(ingredient.id) }) {
            Icon(Icons.Default.Close, contentDescription = null)
        }
    }
}

@Composable
fun AddIngredientButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(12.dp)
    ) {
        Text("Agregar ingrediente")
    }
}

@Composable
fun SaveButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text("Guardar receta")
    }
}

@Preview
@Composable
fun CreateRecipeScreenPreview() {
    CreateRecipeScreen()
}