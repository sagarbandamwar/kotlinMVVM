package com.example.mvvm_kotlin.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvm_kotlin.model.Blog

@Database(entities = [Blog::class], version = 1, exportSchema = false)
abstract class BlogsDatabase : RoomDatabase() {
    abstract fun blogsDao(): BlogsDao

    companion object {
        @Volatile
        private var INSTANCE: BlogsDatabase? = null

        fun getDatabase(context: Context): BlogsDatabase? {
            if (INSTANCE == null) {
                synchronized(BlogsDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            BlogsDatabase::class.java, "blog_table"
                        ).allowMainThreadQueries()
                            .build()
                    }
                    Log.d("databse", "Created")
                }
            }
            return INSTANCE
        }
    }
}