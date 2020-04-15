package com.example.mvvm_kotlin.model

import com.example.mvvm_kotlin.services.NetworkOperationCallback

interface BlogDataSource {
    fun retrieveBlogs(callbackNetwork: NetworkOperationCallback<Blog>)
    fun cancel()
}