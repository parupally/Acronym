package com.example.acronym

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.acronym.api.Resource
import com.example.acronym.api.Status
import com.example.acronym.models.AcronymItem
import com.example.acronym.models.Lfs
import com.example.acronym.models.Vars
import com.example.acronym.repository.AcronymRepository
import com.example.acronym.viewmodel.AcronymViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class AcronymViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val instantExecutionRule = InstantTaskExecutorRule()

    private val mockRepository = mockk<AcronymRepository>()

    private lateinit var viewModel: AcronymViewModel

    private val observer = mockk<Observer<Status>>(relaxed = true)

    @Before
    fun setUp() {
        viewModel = AcronymViewModel(mockRepository, Dispatchers.IO)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `should notify success`() {
        runTest {
            coEvery { mockRepository.getListOfAcronyms(any()) } returns Resource.success(getDummyData())
            viewModel.status.observeForever(observer)
            viewModel = AcronymViewModel(mockRepository)
            viewModel.onClickSearch("TEST")
            assertEquals(Status.Waiting, viewModel.status.value)
        }
        assertEquals(1, viewModel.acronymList.value?.size)
    }

    @Test
    fun `should notify success and no data available if response is empty`() {
        runTest {
            coEvery { mockRepository.getListOfAcronyms(any()) } returns Resource.success(listOf())
            viewModel.status.observeForever(observer)
            viewModel = AcronymViewModel(mockRepository)
            viewModel.onClickSearch("")
            assertEquals(Status.Waiting, viewModel.status.value)
        }
        val noDataReceived = viewModel.noDataReceived.value
        assertTrue(noDataReceived ?: true)
    }

    private fun getDummyData(): List<AcronymItem> {
        val list = ArrayList<Lfs>()
        val varList = ArrayList<Vars>()
        val vars = Vars("heavy meromyosin", 244, 1971)
        varList.add(vars)

        val lfs = Lfs("heavy meromyosin", 267, 1967, varList)
        list.add(lfs)
        return listOf(AcronymItem("Name", list))

    }
}
