package com.group.findit.ui.data.game

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object for managing the Retrofit client.
 * Provides a pre-configured instance of [Retrofit] and the [ApiService].
 */
object ApiClient {

    // Base URL for the API
    private const val BASE_URL = "http://10.0.2.2:8000/"
    // Alternative base URLs for testing or deployment
    // private const val BASE_URL = "http://192.168.1.114:8000"
    // private const val BASE_URL = "http://juop12:8000/"

    /**
     * Retrofit instance configured with the base URL and Gson converter.
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * API service instance created using the Retrofit client.
     */
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}