package com.example.acronym.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Base url for the API
 */
private const val BASE_URL = "http://www.nactem.ac.uk/"

object RetrofitProvider {
    val client: ApiService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}