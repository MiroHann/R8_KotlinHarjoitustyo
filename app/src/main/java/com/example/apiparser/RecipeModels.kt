package com.example.apiparser

import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Array

data class RecipeResponse(
    val hits: List<RecipeHit>
)
data class Ingredientlist(
    val ingredients: String
)

data class RecipeHit(
    val recipe: Recipe,
)

data class Recipe(
    val label: String,
    val image: String,
    val ingredientLines: List<String>
)