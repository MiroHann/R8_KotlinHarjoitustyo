package com.example.apiparser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class Recipe : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentValues = intent.getStringExtra("TitleText")
        val recipeText = intent.getStringExtra("RecipeText").toString()
        setContent {
            title = intentValues
            MaterialTheme{
                    RecipeText(content = recipeText)
            }
        }
    }
    @Composable
    fun RecipeText(content: String, modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(top = 62.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            AsyncImage(model = "https://upload.wikimedia.org/wikipedia/en/c/c2/Peter_Griffin.png", contentDescription = "Placeholder")
            
            Text(
                text = content,
                modifier = modifier.fillMaxWidth().padding(top = 62.dp),
                textAlign = TextAlign.Center,
            )
            
        }
    }
}
