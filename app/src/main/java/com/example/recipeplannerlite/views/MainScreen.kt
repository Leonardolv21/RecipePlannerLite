package com.example.recipeplannerlite.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeplannerlite.ui.theme.RecipePlannerLiteTheme


@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier
){
    Box{
        Column {
            TopBar()
            FilterSection()
            ResultCount()
            RecipeList()
        }
        FabButton()
    }
}

@Composable
fun FabButton() {

}

@Composable
fun RecipeList() {

}

@Composable
fun ResultCount() {

}

@Composable
fun FilterSection() {

}

@Composable
fun TopBar() {
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
            Text("🥗", fontSize = 26.sp)

            Column(modifier = Modifier.padding(start = 8.dp)){
                Text("PLANIFICADOR", fontSize = 10.sp, color = Color.White.copy(0.7f))
                Text("Mis recetas", fontSize = 18.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
            ){
                Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF2D6A4F))
                Text("Buscar receta...", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListPreview(){
    RecipePlannerLiteTheme() {
        RecipeListScreen()
    }
}