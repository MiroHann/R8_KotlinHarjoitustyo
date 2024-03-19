package com.example.apiparser

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import okhttp3.OkHttpClient
import okhttp3.Request
class MainActivity : ComponentActivity() {
    var Nameofrecepie = "Makkara"
    var Contentofrecepie = "Makkara soppa, 1 Makkara 2 Keittoa 3 Pippuri. Keitä, Makkara ja Syö! (Väliaikainen teksti)"
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
