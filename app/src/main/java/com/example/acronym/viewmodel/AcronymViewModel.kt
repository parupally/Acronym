package com.example.acronym.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acronym.api.Status
import com.example.acronym.models.Lfs
import com.example.acronym.repository.AcronymRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * View Model to handle logic for acronyms
 */

class AcronymViewModel constructor(
    private val repository: AcronymRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    /**
     * Notifies if no data is received.
     */
    private val _noDataReceived = MutableLiveData<Boolean>()
    val noDataReceived: LiveData<Boolean> = _noDataReceived

    /**
     * Notifies the Acronym List.
     */
    private val _acronymList = MutableLiveData<List<Lfs>?>()
    val acronymList: MutableLiveData<List<Lfs>?> = _acronymList

    /**
     * Provides the status of the API call.
     */
    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status


    /**
     * Get the list of acronyms when user searches.
     * @param word from user input
     */
    fun onClickSearch(word: String) {
        _status.postValue(Status.Waiting)
        val exceptionHandler = CoroutineExceptionHandler { _, _ ->
            _status.postValue(Status.ERROR)
        }
        viewModelScope.launch(dispatcher + exceptionHandler) {
            val response = repository.getListOfAcronyms(word = word)
            if (response.status == Status.SUCCESS) {
                val details = response.data
                if (details.isNullOrEmpty()) {
                    _noDataReceived.postValue(true)
                } else {
                    // We can convert the response model to Domain objects that can provide data to UI.
                    details.forEach {
                        _acronymList.postValue(it.lfs)
                    }
                }
            }
            _status.postValue(response.status)

        }

    }

    fun showResultVisibility(): Int {
        return if (_noDataReceived.value == true) View.VISIBLE else View.GONE
    }
}