package com.example.acronym.repository

import com.example.acronym.api.Resource
import com.example.acronym.models.AcronymItem

/**
 * Consists of list of Api calls for the acronym.
 */

interface AcronymRepository {
    suspend fun getListOfAcronyms(word: String): Resource<List<AcronymItem>?>
}