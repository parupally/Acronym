package com.example.acronym.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.acronym.R
import com.example.acronym.api.RetrofitProvider
import com.example.acronym.api.Status
import com.example.acronym.databinding.ActivityMainBinding
import com.example.acronym.models.Lfs
import com.example.acronym.repository.AcronymRepository
import com.example.acronym.repository.AcronymRepositoryImpl
import com.example.acronym.viewmodel.AcronymViewModel
import kotlinx.coroutines.Dispatchers.IO

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val acronymAdapter = AcronymAdapter()
    private lateinit var viewModel: AcronymViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val repository: AcronymRepository =
            AcronymRepositoryImpl(RetrofitProvider.client)
        viewModel = AcronymViewModel(repository, IO)
        binding.lifecycleOwner = this
        binding.acronymViewModel = viewModel
        initializeRv()
        setObservers()
    }

    private fun setObservers() {
        viewModel.acronymList.observe(this) {
            with(acronymAdapter) {
                acronymList.clear()
                addData(it as MutableList<Lfs>)
            }
            binding.tvNoResult.visibility = View.GONE
        }
        viewModel.noDataReceived.observe(this) {
            binding.tvNoResult.visibility = View.VISIBLE
            Toast.makeText(this, R.string.no_data_message, Toast.LENGTH_LONG).show()
        }
        viewModel.status.observe(this) {
            if (Status.Waiting == it) {
                with(acronymAdapter) {
                    acronymList.clear()
                    notifyDataSetChanged()
                }
            }
            hideKeyBoard()
        }
    }

    private fun hideKeyBoard() {
        val inputManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun initializeRv() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = acronymAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }
}