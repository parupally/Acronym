package com.example.acronym.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.acronym.databinding.AcronymAdapterBinding
import com.example.acronym.models.Lfs

class AcronymAdapter :
    RecyclerView.Adapter<AcronymAdapter.ViewHolder>() {


    var acronymList: MutableList<Lfs> = mutableListOf()

    inner class ViewHolder(private val binding: AcronymAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(acronymItem: Lfs) {
            binding.acronym = acronymItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AcronymAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(acronymList[position])
    }

    override fun getItemCount(): Int {
        return acronymList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(list: MutableList<Lfs>) {
        acronymList = list
        notifyDataSetChanged()
    }
}