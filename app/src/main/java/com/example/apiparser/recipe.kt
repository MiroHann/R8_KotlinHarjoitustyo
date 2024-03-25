package com.example.apiparser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
class RecipePage : AppCompatActivity() {

  private var arrayInstructions = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        val intentValues = intent.getStringExtra("TitleText").toString()
        arrayInstructions  = intent.getStringArrayExtra("RecipeText")?.toMutableList()!!
        val imageURL = intent.getStringExtra("Image").toString()
        setContent {
            MaterialTheme{
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
                fontWeight = Bold,
                fontSize = 20.sp,
            )
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
                                .padding(top = 22.dp,bottom = 22.dp)
                            ,
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp,
                        )
                    }

                }
            }
        }
    }


