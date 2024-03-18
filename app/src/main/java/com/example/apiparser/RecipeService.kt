package com.example.apiparser

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.Query

interface RecipeService {
    @GET("api/recipes/v2?type=public")
    suspend fun getRecipes(
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String,
        @Query("cuisineType") cuisineType: String,
        @Query("imageSize") imageSize: String
    ): RecipeResponse
}

fun createRecipeService(): RecipeService {
    // Create an OkHttp client with logging interceptor
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    // Create Retrofit instance with the OkHttp client
    return Retrofit.Builder()
        .baseUrl("https://api.edamam.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RecipeService::class.java)
}