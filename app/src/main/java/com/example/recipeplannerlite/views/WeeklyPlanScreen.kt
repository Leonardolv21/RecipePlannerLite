package com.example.recipeplannerlite.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeplannerlite.ViewModels.WeeklyPlanViewModel
import com.example.recipeplannerlite.models.Recipe
import com.example.recipeplannerlite.models.ShoppingItem

@Composable
fun WeeklyPlanScreen(
    vm: WeeklyPlanViewModel = viewModel(),
    onNavigateToShopping: () -> Unit,
    onBack: () -> Unit
) {
    val state = vm.state.collectAsState().value

    LaunchedEffect(Unit) {
        vm.reloadRecipes()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        WeeklyPlanTopBar(onBack = onBack)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(state.plan.keys.toList()) { day ->
                DayRow(
                    day = day,
                    selectedRecipe = state.plan[day],
                    availableRecipes = state.availableRecipes,
                    onRecipeSelected = { recipeId ->
                        vm.assignRecipeToDay(day, recipeId)
                    }
                )
            }
        }

        Button(
            onClick = onNavigateToShopping,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text("Ver lista de compras")
        }
    }
}

@Composable
fun WeeklyPlanTopBar(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    listOf(Color(0xFF2D6A4F), Color(0xFF52B788))
                )
            )
            .padding(16.dp)
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text("←", color = Color.White, fontSize = 20.sp)
        }
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text("Plan semanal", color = Color.White, fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Text("Asigna una receta por día", color = Color.White.copy(0.7f), fontSize = 11.sp)
        }
    }
}

@Composable
fun DayRow(
    day: String,
    selectedRecipe: Recipe?,
    availableRecipes: List<Recipe>,
    onRecipeSelected: (Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = day,
            modifier = Modifier.width(90.dp),
            fontSize = 13.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
        )

        Button(
            onClick = { showDialog = true },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedRecipe != null) Color(0xFF2D6A4F) else Color.LightGray
            )
        ) {
            Text(
                text = selectedRecipe?.name ?: "Seleccionar",
                fontSize = 11.sp,
                color = if (selectedRecipe != null) Color.White else Color.DarkGray
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Elige una receta para $day") },
            text = {
                LazyColumn {
                    items(availableRecipes) { recipe ->
                        TextButton(
                            onClick = {
                                onRecipeSelected(recipe.id)
                                showDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(recipe.name)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun ShoppingListScreen(
    vm: WeeklyPlanViewModel = viewModel(),
    onBack: () -> Unit
) {
    val state = vm.state.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        ShoppingTopBar(onBack = onBack)

        if (state.shoppingList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay ingredientes aún.\nAsigna recetas al plan semanal.", color = Color.Gray)
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(state.shoppingList) { item ->
                    ShoppingItemRow(
                        item = item,
                        onToggle = { vm.toggleIngredientBought(item.name) }
                    )
                }
            }
        }
    }
}

@Composable
fun ShoppingTopBar(onBack: () -> Unit) {
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
            Text("←", color = Color.White, fontSize = 20.sp)
        }
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Lista de compras", color = Color.White, fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Text("Generada del plan semanal", color = Color.White.copy(0.7f), fontSize = 10.sp)
        }
    }
}

@Composable
fun ShoppingItemRow(
    item: ShoppingItem,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = item.isBought,
            onCheckedChange = { onToggle() }
        )
        Text(
            text = item.name,
            modifier = Modifier.weight(1f).padding(start = 8.dp),
            color = if (item.isBought) Color.Gray else Color.Unspecified,
            style = if (item.isBought)
                LocalTextStyle.current.copy(textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough)
            else
                LocalTextStyle.current
        )
        Text(
            text = item.quantity,
            color = if (item.isBought) Color.Gray else Color(0xFF2D6A4F)
        )
    }
    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
}