package com.example.mvvm_kotlin.util

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi

class CommonUtil {
    companion object {
        @Suppress("DEPRECATION")
        @RequiresApi(Build.VERSION_CODES.M)
        fun isOnline(context: Context?): Boolean {   // is online method will return user is connected or not
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}