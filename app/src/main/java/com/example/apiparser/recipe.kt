package com.example.apiparser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
class RecipePage : AppCompatActivity() {

  private var arrayInstructions = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intentValues = intent.getStringExtra("TitleText")
        arrayInstructions  = intent.getStringArrayExtra("RecipeText")?.toMutableList()!!
        val imageURL = intent.getStringExtra("Image").toString()
        setContent {
            title = intentValues
            MaterialTheme{
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeText(image = imageURL)
                }

            }
        }
    }
    @Composable
    fun RecipeText(image: String, modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(top = 32.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            AsyncImage(model = image, contentDescription = "Placeholder", Modifier.fillMaxWidth())
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {

                    items(arrayInstructions){ content ->
                        Text(
                            text = content,
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(top = 22.dp)
                            ,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }


