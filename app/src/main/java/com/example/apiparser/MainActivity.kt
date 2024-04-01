package com.example.apiparser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.apiparser.ui.theme.APIParserTheme
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APIParserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavDrawer()
                }
            }
        }
    }

    fun TempRecipepage(Image: String, Name: String, Content: List<String>, isSaved: Boolean) {
        val intent = Intent(this@MainActivity, RecipePage::class.java)
        val array = Content.toTypedArray()
        intent.putExtra("TitleText", Name)
        intent.putExtra("RecipeText", array)
        intent.putExtra("Image", Image)
        intent.putExtra("IsSaved", isSaved)
        startActivity(intent)
    }

    @Composable
    fun RecipeList(modifier: Modifier = Modifier, contentPadding: PaddingValues) {

        val (cuisineType, setCuisineType) = remember { mutableStateOf("Nordic") }
        val (sortedAlphabetically, setSortedAlphabetically) = remember { mutableStateOf(false) }

        val service = remember { createRecipeService() }
        val recipes = remember { mutableStateListOf<Recipe>() }

        LaunchedEffect(cuisineType, sortedAlphabetically) {
            try {
                val response = service.getRecipes(
                    appId = "7a7668fb",
                    appKey = "5dedd41ca0ac7b45881b37db8ae94423",
                    cuisineType = cuisineType,
                    imageSize = "REGULAR"
                )
                recipes.clear()
                recipes.addAll(response.hits.map { it.recipe })

                if (sortedAlphabetically) {
                    recipes.sortBy { it.label }
                } else {
                    recipes.sortByDescending { it.label }
                }
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
                ) {
                    Image(
                        painter = rememberImagePainter(data = recipe.image),
                        contentDescription = "Recipe Image",
                        modifier = Modifier
                            .size(width = 370.dp, height = 300.dp)
                            .clickable {
                                TempRecipepage(
                                    recipe.image,
                                    recipe.label,
                                    recipe.ingredientLines,
                                    isSaved = false
                                )
                            },
                        contentScale = ContentScale.Crop,
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
        Row(
            modifier = modifier.fillMaxWidth(),
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
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart,
        ) {
            Button(
                onClick = { setSortedAlphabetically(!sortedAlphabetically) },
                modifier = Modifier.padding(16.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = if (sortedAlphabetically) "Sort Z -> A" else "Sort A -> Z",
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }

    private fun getSavedRecipes(): List<Recipe> {
        val sharedPreferences = getSharedPreferences("SavedRecipes", Context.MODE_PRIVATE)
        val savedRecipesJsonMap = sharedPreferences.all

        val savedRecipes = mutableListOf<Recipe>()
        val gson = Gson()

        for ((_, value) in savedRecipesJsonMap) {
            val recipeJson = value as String
            val recipe = gson.fromJson(recipeJson, Recipe::class.java)
            savedRecipes.add(recipe)
        }

        return savedRecipes
    }

    @Composable
    fun SavedRecipesList(
        modifier: Modifier = Modifier,
        contentPadding: PaddingValues,
        onItemClick: (Recipe, Boolean) -> Unit,
    ) {
        val savedRecipes = getSavedRecipes()

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(savedRecipes) { recipe ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = recipe.label,
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .clickable {
                                onItemClick(
                                    recipe,
                                    true
                                )
                            },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    @Composable
    @Preview
    fun NavDrawer(modifier: Modifier = Modifier) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var currentScreen by remember { mutableStateOf(1) }
        val scope = rememberCoroutineScope()
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Divider()
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = "Home",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                            selected = false,
                            onClick = {
                                currentScreen = 1
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                        )
                        Divider()
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = "Saved",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                            selected = false,
                            onClick = {
                                currentScreen = 2
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        )
                        Divider()
                    }
                }
            },
        ) {
            Scaffold(
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        text = { Text("Show drawer", color = Color.White) },
                        icon = {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = "",
                                tint = Color.White
                            )
                        },
                        onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.secondary
                    )
                },
                floatingActionButtonPosition = FabPosition.End
            ) { contentPadding ->
                // Screen content
                if (currentScreen == 1) {
                    RecipeList(modifier = modifier, contentPadding = contentPadding)
                } else if (currentScreen == 2) {
                    SavedRecipesList(
                        modifier = modifier,
                        contentPadding = contentPadding,
                        onItemClick = { recipe, isSaved ->
                            TempRecipepage(
                                recipe.image,
                                recipe.label,
                                recipe.ingredientLines,
                                isSaved
                            )

                        }
                    )
                    }
                }
            }
        }
    }
