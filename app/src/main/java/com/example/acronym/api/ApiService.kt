package com.example.acronym.api

import com.example.acronym.models.AcronymItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface to maintain the methods for the api calls.
 */
interface ApiService {
    /**
     * To get the meaning using the input string name.
     * @param sf acronym search word
     */

    @GET("software/acromine/dictionary.py")
    suspend fun getAcronyms(
        @Query("sf") word: String
    ): Response<List<AcronymItem>>

}