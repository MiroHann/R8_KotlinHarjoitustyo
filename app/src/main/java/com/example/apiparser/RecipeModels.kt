package com.example.apiparser

data class RecipeResponse(
    val hits: List<RecipeHit>
)

data class RecipeHit(
    val recipe: Recipe
)

data class Recipe(
    val label: String,
    val image: String
)