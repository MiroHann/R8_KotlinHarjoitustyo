package com.example.apiparser

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.apiparser.ui.theme.APIParserTheme
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APIParserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeList()
                }
            }
        }
    }

    fun TempRecipepage(Image:String, Name:String, Content:List<String>) {
        val intent = Intent(this@MainActivity, RecipePage::class.java)
        val array = Content.toTypedArray()
        intent.putExtra("TitleText", Name)
        intent.putExtra("RecipeText", array)
        intent.putExtra("Image", Image)
        println(Image)
        println(Name)
        println(Content)
        startActivity(intent)
    }
    @Preview
    @Composable
    fun RecipeList(modifier: Modifier = Modifier) {

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
                            modifier = Modifier.size(width = 370.dp, height = 300.dp).clickable { TempRecipepage(recipe.image, recipe.label,recipe.ingredientLines)  },
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
    }
}
