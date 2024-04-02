package com.example.apiparser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.gson.Gson

class RecipePage : AppCompatActivity() {

    private lateinit var arrayInstructions: MutableList<String>
    private lateinit var intentValues: String
    private lateinit var imageURL: String
    private var isSaved: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        intentValues = intent.getStringExtra("TitleText").toString()
        arrayInstructions = intent.getStringArrayExtra("RecipeText")?.toMutableList()!!
        imageURL = intent.getStringExtra("Image").toString()
        isSaved = intent.getBooleanExtra("IsSaved", false)
        setContent {
            MaterialTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeText(image = imageURL, header = intentValues)
                }
            }
        }
    }

    @Composable
    fun RecipeText(image: String, header: String, modifier: Modifier = Modifier) {

        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(top = 32.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = modifier.padding(bottom = 20.dp),
                text = header,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            AsyncImage(model = image, contentDescription = "Placeholder", Modifier.fillMaxWidth())
            LazyColumn(
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                items(arrayInstructions) { content ->
                    Text(
                        text = content,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 22.dp, bottom = 22.dp)
                        ,
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                    )
                }
            }
            SaveButton(isSaved)
        }
    }

    @Composable
    fun SaveButton(isSaved: Boolean) {
        Button(
            onClick = { toggleSaveRecipe() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(if (isSaved) "Unsave Recipe" else "Save Recipe")
        }
    }

    private fun toggleSaveRecipe() {
        if (isSaved) {
            val sharedPreferences = getSharedPreferences("SavedRecipes", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove(intentValues)
            editor.apply()
            Log.d("RecipePage", "Recipe unsaved locally: Label: $intentValues")

            isSaved = false
        } else {
            val recipeToSave = Recipe(
                label = intentValues,
                image = imageURL,
                ingredientLines = arrayInstructions
            )
            val recipeJson = Gson().toJson(recipeToSave)
            val sharedPreferences = getSharedPreferences("SavedRecipes", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(recipeToSave.label, recipeJson)
            editor.apply()
            Log.d("RecipePage", "Recipe saved locally: Label: ${recipeToSave.label}, Ingredients: ${recipeToSave.ingredientLines}")

            isSaved = true
        }

        if(isSaved){
            finish()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}