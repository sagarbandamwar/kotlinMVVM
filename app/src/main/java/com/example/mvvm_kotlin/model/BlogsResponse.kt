package com.example.mvvm_kotlin.model

data class BlogsResponse(val title: String?, val rows: List<Blog>?) {
    fun isSuccess(): Boolean = (rows?.isNotEmpty() == true)
}