package com.example.mvvm_kotlin.di

import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_kotlin.model.BlogDataSource
import com.example.mvvm_kotlin.model.BlogRepository
import com.example.mvvm_kotlin.viewmodel.ViewModelFactory

object Injection {
    private val BLOG_DATA_SOURCE: BlogDataSource = BlogRepository()
    private val blogsViewModelFactory = ViewModelFactory(BLOG_DATA_SOURCE)

    fun provideViewModelFactory(): ViewModelProvider.Factory{
        return blogsViewModelFactory
    }
}