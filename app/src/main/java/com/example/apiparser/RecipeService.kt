package com.example.apiparser

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface RecipeService {
    @GET("https://api.edamam.com/api/recipes/v2?type=public&app_id=7a7668fb&app_key=5dedd41ca0ac7b45881b37db8ae94423&cuisineType=Nordic&imageSize=REGULAR")
    suspend fun getRecipes(
        @Header("7a7668fb") appId: String,
        @Header("5dedd41ca0ac7b45881b37db8ae94423") appKey: String
    ): RecipeResponse
}

fun createRecipeService(): RecipeService {
    return Retrofit.Builder()
        .baseUrl("https://api.edamam.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RecipeService::class.java)
}