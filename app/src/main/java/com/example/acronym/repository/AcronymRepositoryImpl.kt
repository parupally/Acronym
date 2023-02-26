package com.example.acronym.repository

import com.example.acronym.api.ApiService
import com.example.acronym.api.Resource
import com.example.acronym.api.RetrofitProvider
import com.example.acronym.models.AcronymItem

/**
 *
 * Implementation class for [AcronymRepository]
 */

class AcronymRepositoryImpl constructor(
    private val apiService: ApiService = RetrofitProvider.client
) : AcronymRepository {
    /**
     * Get list of acronyms
     */

    override suspend fun getListOfAcronyms(word: String): Resource<List<AcronymItem>?> {
        val response = apiService.getAcronyms(word)
        return if (response.isSuccessful) {
            Resource.success(response.body())
        } else {
            Resource.error(response.message())
        }
    }
}

