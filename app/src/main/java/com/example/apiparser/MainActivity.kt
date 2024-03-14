package com.example.apiparser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apiparser.ui.theme.APIParserTheme
import com.google.android.material.internal.ContextUtils.getActivity


class MainActivity : ComponentActivity() {
    var Nameofrecepie = "Makkara"
    var Contentofrecepie = "This is a placeholder text!"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainmenu_layout)
        findViewById<Button>(R.id.buttonWindow)
            .setOnClickListener {
                val intent = Intent(this, Recipe::class.java)
                intent.putExtra("TitleText", Nameofrecepie)
                intent.putExtra("RecipeText", Contentofrecepie)
                startActivity(intent)
            }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    APIParserTheme {
        Greeting("Android")
    }
}