package com.example.apiparser

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.apiparser.ui.theme.APIParserTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APIParserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //RecipeList()
                    NavDrawer()
                }
            }
        }
    }
}

@Composable
fun RecipeList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {

    val (cuisineType, setCuisineType) = remember { mutableStateOf("Nordic") }

    val service = remember { createRecipeService() }
    val recipes = remember { mutableStateListOf<Recipe>() }

    LaunchedEffect(cuisineType) {
        try {
            val response = service.getRecipes(
                appId = "7a7668fb",
                appKey = "5dedd41ca0ac7b45881b37db8ae94423",
                cuisineType = cuisineType,
                imageSize = "REGULAR"
            )
            recipes.clear()
            recipes.addAll(response.hits.map { it.recipe })
        } catch (e: Exception) {
            Log.e("API_ERROR", "Failed to fetch recipes", e)
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(recipes) { recipe ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Image(
                    painter = rememberImagePainter(data = recipe.image),
                    contentDescription = "Recipe Image",
                    modifier = Modifier.size(width = 370.dp, height = 300.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = recipe.label,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 4.dp),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
    Row( modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
    ) {
        Button(
            onClick = { setCuisineType("British") },
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(0.dp),

        ) {
            Image(
                painter = painterResource(id = R.drawable.british),
                contentDescription = "Button Image",
                modifier = Modifier
                    .size(width = 100.dp, height = 45.dp)
            )
        }
        Button(
            onClick = { setCuisineType("American") },
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(0.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.america),
                contentDescription = "Button Image",
                modifier = Modifier
                    .size(width = 100.dp, height = 45.dp)
            )
        }
        Button(
            onClick = { setCuisineType("Mexican") },
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(0.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.mexico),
                contentDescription = "Button Image",
                modifier = Modifier
                    .size(width = 100.dp, height = 45.dp)
            )
        }
        Button(
            onClick = { setCuisineType("Japanese") },
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(0.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.japan),
                contentDescription = "Button Image",
                modifier = Modifier
                    .size(width = 100.dp, height = 45.dp)
            )
        }

        }
    }


@Composable
@Preview
fun NavDrawer(modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column ( verticalArrangement = Arrangement.Center ) {
                    NavigationDrawerItem(
                        label = { Text(text = "Frontpage", textAlign = TextAlign.Center) },
                        selected = false,
                        onClick = { /*TODO*/ },
                    )
                    Divider()
                    NavigationDrawerItem(
                        label = { Text(text = "Saved", textAlign = TextAlign.Center)  },
                        selected = false,
                        onClick = { /*TODO*/ }
                    )
                }
            }
        },
    ) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Show drawer") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            },
            floatingActionButtonPosition = FabPosition.End, // Positioning the FAB in the top right corner
        ) { contentPadding ->
            // Screen content
            RecipeList(modifier = modifier, contentPadding = contentPadding)
        }
    }
}