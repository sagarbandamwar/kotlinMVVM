package com.example.mvvm_kotlin.services

interface NetworkOperationCallback<T> {
    fun onSuccess(data: List<T>?)
    fun onError(error: String?)
}