package com.example.mvvm_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_kotlin.model.BlogDataSource

class ViewModelFactory(private val repository: BlogDataSource): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BlogsViewModel(repository) as T
    }
}