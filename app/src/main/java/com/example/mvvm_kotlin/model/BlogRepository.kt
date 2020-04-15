package com.example.mvvm_kotlin.model

import android.telecom.Call
import android.util.Log
import com.example.mvvm_kotlin.services.ApiClient
import com.example.mvvm_kotlin.services.NetworkOperationCallback
import retrofit2.Callback
import retrofit2.Response

const val TAG = "CONSOLE"

class BlogRepository : BlogDataSource {

    private var call: retrofit2.Call<BlogsResponse>? = null

    override fun retrieveBlogs(callbackNetwork: NetworkOperationCallback<Blog>) {
        call = ApiClient.build()?.museums()
        call?.enqueue(object : Callback<BlogsResponse> {
            override fun onFailure(call: retrofit2.Call<BlogsResponse>, t: Throwable) {
                callbackNetwork.onError(t.message)
            }

            override fun onResponse(
                call: retrofit2.Call<BlogsResponse>,
                response: Response<BlogsResponse>
            ) {
                response.body()?.let {
                    if (response.isSuccessful && (it.isSuccess())) {
                        Log.v(TAG, "data ${it.rows}")
                        callbackNetwork.onSuccess(it.rows)
                    } else {
                        callbackNetwork.onError("Error")
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.cancel()
    }
}