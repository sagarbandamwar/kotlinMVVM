package com.example.mvvm_kotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_kotlin.model.Blog
import com.example.mvvm_kotlin.model.BlogDataSource
import com.example.mvvm_kotlin.services.NetworkOperationCallback


class BlogsViewModel(private val repository: BlogDataSource) : ViewModel() {

    private val blogs = MutableLiveData<List<Blog>>().apply { value = emptyList() }
    val blogsLiveData: LiveData<List<Blog>> = blogs

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun loadBlogs() {
        _isViewLoading.postValue(true)
        repository.retrieveBlogs(object : NetworkOperationCallback<Blog> {
            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(error)
            }

            override fun onSuccess(data: List<Blog>?) {
                _isViewLoading.postValue(false)
                if (data != null) {
                    if (data.isEmpty()) {
                        _isEmptyList.postValue(true)
                    } else {
                        blogs.value = data
                    }
                }
            }
        })
    }
}