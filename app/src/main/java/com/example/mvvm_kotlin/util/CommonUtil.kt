package com.example.mvvm_kotlin.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.mvvm_kotlin.R


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

        fun showDialog(title: String, context: Context) { // this method will show dialog
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_dialog)
            val body = dialog.findViewById(R.id.tvBody) as TextView
            body.text = title
            val yesBtn = dialog.findViewById(R.id.btn_yes) as Button
            val noBtn = dialog.findViewById(R.id.btn_no) as Button
            val bundle = Bundle()
            // val noBtn = dialog .findViewById(R.id.noBtn) as TextView
            yesBtn.setOnClickListener {
                ActivityCompat.startActivityForResult(
                    context as Activity,
                    Intent(Settings.ACTION_SETTINGS),
                    0,
                    bundle
                )
                dialog.dismiss()
            }

            noBtn.setOnClickListener {
                dialog.dismiss()
            }
            //  noBtn.setOnClickListener { dialog .dismiss() }
            dialog.show()
        }
    }
}