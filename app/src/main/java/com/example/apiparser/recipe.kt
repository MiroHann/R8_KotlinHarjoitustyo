package com.example.apiparser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window

class Recipe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.recipe)
    }
}