package com.example.recipeplannerlite.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeplannerlite.ViewModels.CreateRecipeViewModel
import com.example.recipeplannerlite.models.EditableIngredient

@Composable
fun CreateRecipeScreen(
    vm: CreateRecipeViewModel = viewModel(),
    onBack: () -> Unit
){
    val state = vm.state.collectAsState().value
    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onBack()
        }
    }
    Column {
        TopBarCreate(onBack = onBack)

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
fun IngredientHeader(count : Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("🧂", fontSize = 18.sp)
            Text(
                "Ingredientes",
                modifier = Modifier.padding(start = 8.dp),
                fontSize = 15.sp
            )
        }

        Box(
            modifier = Modifier
                .background(Color(0xFFD8F3DC), RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                "$count añadidos",
                color = Color(0xFF2D6A4F),
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun DescriptionSection(
    value: String,
    onChange: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("💬")
                Text(
                    "Descripción",
                    modifier = Modifier.padding(start = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = value,
                onValueChange = onChange,
                placeholder = { Text("Descripción (opcional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            )
        }
    }
}

@Composable
fun NameSection(
    name: String,
    emoji: String,
    onNameChange: (String) -> Unit,
    onEmojiSelect: (String) -> Unit
) {
    val emojiList = listOf("🍝", "🥗", "🌮", "🍲", "🍛", "🥘")

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("📝")
                Text(
                    "Nombre de la receta",
                    modifier = Modifier.padding(start = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                // Emoji seleccionado
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(Color(0xFFD8F3DC), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(emoji, fontSize = 26.sp)
                }

                TextField(
                    value = name,
                    onValueChange = onNameChange,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f),
                    placeholder = { Text("Nombre...") }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text("Elige un ícono", fontSize = 10.sp, color = Color.Gray)

            LazyRow {
                items(emojiList) { item ->
                    val isSelected = item == emoji

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(38.dp)
                            .background(
                                if (isSelected) Color(0xFF2D6A4F)
                                else Color(0xFFD8F3DC),
                                RoundedCornerShape(9.dp)
                            )
                            .clickable { onEmojiSelect(item) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(item)
                    }
                }
            }
        }
    }
}

@Composable
fun TopBarCreate(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    listOf(Color(0xFF2D6A4F), Color(0xFF52B788))
                )
            )
            .padding(14.dp)
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Nueva Receta", color = Color.White, fontSize = 16.sp)
            Text(
                "Completa los campos",
                color = Color.White.copy(0.7f),
                fontSize = 10.sp
            )
        }
    }
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
    CreateRecipeScreen(
        onBack = {}
    )

}