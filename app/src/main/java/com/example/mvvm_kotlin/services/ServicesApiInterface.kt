package com.example.mvvm_kotlin.services

import com.example.mvvm_kotlin.model.BlogsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ServicesApiInterface {
    @GET("facts")
    fun museums(): Call<BlogsResponse>
}