package com.example.acronym

import com.example.acronym.api.ApiService
import com.example.acronym.api.Status
import com.example.acronym.models.AcronymItem
import com.example.acronym.repository.AcronymRepository
import com.example.acronym.repository.AcronymRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AcronymRepositoryImplTest {

    private val mockService = mockk<ApiService>(relaxed = true)
    private lateinit var repository: AcronymRepository

    @Before
    fun setUp() {
        repository = AcronymRepositoryImpl(mockService)
    }

    @Test
    fun `should return Resource as success with data when api successfully retrieves the list`() =
        runTest {
            val mockResponse = listOf<AcronymItem>(mockk())
            coEvery {
                mockService.getAcronyms(any())
            } returns Response.success(mockResponse)
            val repository = AcronymRepositoryImpl(mockService)
            val response = repository.getListOfAcronyms("Test")
            assertNotNull(response)
            assertEquals(Status.SUCCESS, response.status)
            assertEquals(1, response.data?.size)
        }

    @Test
    fun `should return Resource as Error with message when Api call is failed`() {
        coEvery {
            mockService.getAcronyms(any())
        } returns Response.error(
            400,
            ResponseBody.create(MediaType.parse("UTF-8"), "Failed to get Response")
        )
        runTest {
            val response = repository.getListOfAcronyms("Test")
            assertNotNull(response)
            assertEquals(Status.ERROR, response.status)
        }

    }
}