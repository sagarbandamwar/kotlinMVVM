package com.example.mvvm_kotlin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mvvm_kotlin.model.Blog

@Dao
interface BlogsDao {
    @Insert
    fun setBlogs(blogs: List<Blog>)

    @Query("SELECT * from blog_table")
    fun getBlogs(): List<Blog>
}