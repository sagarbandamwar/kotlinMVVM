package com.example.mvvm_kotlin.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm_kotlin.R
import com.example.mvvm_kotlin.R.layout.activity_blogs

class BlogsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = BlogsFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(activity_blogs)
        initFragment()
    }

    private fun initFragment() {
        // adding fragment to the mai activity
        if (supportFragmentManager.fragments.size == 0) {
            supportFragmentManager.beginTransaction()
                .add(R.id.root_layout, BlogsFragment(), "BlogList")
                .commit()
        }
    }
}
