package com.example.recipeplannerlite.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
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
import com.example.recipeplannerlite.ui.theme.RecipePlannerLiteTheme
import com.example.recipeplannerlite.ViewModels.RecipeListViewModel
import com.example.recipeplannerlite.models.Recipe
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    vm: RecipeListViewModel = viewModel(),
    onNavigateToCreate: () -> Unit,
    onNavigateToWeekly: () -> Unit
){
    val state = vm.state.collectAsState().value
    LaunchedEffect(true) {
        vm.reloadRecipes()
    }

    Box(modifier = Modifier.fillMaxSize()){
        Column {
            TopBar(
                search = state.searchQuery,
                onSearchChange = vm::updateSearch
            )

            FilterSection(
                availableFilters = vm.availableFilters,
                selectedFilters = state.selectedFilters,
                onFilterClick = vm::toggleFilter
            )

            ResultCount(state.filteredRecipes.size)

            RecipeList(state.filteredRecipes)
        }

        FabButton(onNavigateToCreate,
            onNavigateToWeekly = onNavigateToWeekly
        )
    }
}

@Composable
fun FabButton(
    onNavigateToCreate: () -> Unit,
    onNavigateToWeekly: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onNavigateToWeekly,
                shape = RoundedCornerShape(50)
            ) {
                Text("Plan semanal")
            }

            Button(
                onClick = onNavigateToCreate,
                shape = RoundedCornerShape(50)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Text("Nueva Receta")
            }
        }
    }
}

@Composable
fun RecipeList(recipes: List<Recipe>) {
    LazyColumn (contentPadding = PaddingValues(bottom = 80.dp)
    ){
        items(recipes) { recipe ->
            RecipeCard(recipe)
        }
    }
}
@Composable
fun RecipeCard( recipe: Recipe) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(recipe.name)
                    Text(recipe.description, fontSize = 12.sp, color = Color.Gray)
                }

            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )

            Text("INGREDIENTES", fontSize = 10.sp, color = Color.Gray)

            recipe.ingredients.forEach {
                IngredientItem(it.name, it.quantity)
            }
        }
    }
}
@Composable
fun IngredientItem(name: String, qty: String) {
    Row(
        modifier = Modifier.padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(Color(0xFF52B788), CircleShape)
        )

        Text(name, modifier = Modifier.weight(1f).padding(start = 6.dp))
        Text(qty, color = Color(0xFF2D6A4F))
    }
}
@Composable
fun ResultCount(count : Int) {
    Box(modifier = Modifier.padding(12.dp)) {
        Text(
            "$count recetas",
            modifier = Modifier
                .background(Color(0xFFD8F3DC), RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun FilterSection(
    availableFilters: List<String>,
    selectedFilters: List<String>,
    onFilterClick: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
    ) {
        items(availableFilters) { filter ->
            val isSelected = selectedFilters.contains(filter)

            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .background(
                        if (isSelected) Color(0xFF2D6A4F)
                        else Color(0xFFD8F3DC),
                        RoundedCornerShape(20.dp)
                    )
                    .clickable { onFilterClick(filter) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = filter,
                    color = if (isSelected) Color.White else Color(0xFF2D6A4F),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun TopBar(search: String, onSearchChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    listOf(Color(0xFF2D6A4F), Color(0xFF52B788))
                )
            )
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text("PLANIFICADOR")
                Text("Mis recetas")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Search, contentDescription = null)
            TextField(
                value = search,
                onValueChange = onSearchChange,
                placeholder = { Text("Buscar receta...") }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListPreview(){
    RecipePlannerLiteTheme {
        RecipeListScreen(
            onNavigateToCreate = {},
            onNavigateToWeekly = {}
        )
    }
}