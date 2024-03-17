package com.example.apiparser

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apiparser.ui.theme.APIParserTheme
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File
import java.io.InputStream

var returnString: String? = null
class MainActivity : ComponentActivity() {
    var Nameofrecepie = "Makkara"
    var Contentofrecepie = "Makkara soppa, 1 Makkara 2 Keittoa 3 Pippuri. Keitä, Makkara ja Syö! (Väliaikainen teksti)"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val threadWithRunnable = Thread(ExecuteThread())
        threadWithRunnable.start()
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
 class ExecuteThread :  Thread() {
      override fun run() {
          val client = OkHttpClient()
         val request =  Request.Builder().url("https://api.edamam.com/api/recipes/v2?type=public&q=chicken&app_id=7a7668fb&app_key=5dedd41ca0ac7b45881b37db8ae94423").build()
          client.newCall(request).execute().use() {
              response -> returnString

          }
     }
}