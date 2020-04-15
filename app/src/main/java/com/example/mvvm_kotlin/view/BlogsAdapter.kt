package com.example.mvvm_kotlin.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mvvm_kotlin.R
import com.example.mvvm_kotlin.model.Blog
import kotlinx.android.synthetic.main.row_blogs.view.*

class BlogsAdapter(private var blogs: List<Blog>) :
    RecyclerView.Adapter<BlogsAdapter.MViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_blogs, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        val blog = blogs[position]

        //render
        with(vh.itemView) {
            title.text = blog.title
            subtitle.text = blog.description
            blog.imageHref?.let {
                Glide.with(context).load(blog.imageHref).apply(RequestOptions().override(160, 140)).into(image)
            }
            /*     Picasso.get()
                     .load(blog.imageHref)                          // we are loading images with the help of picasso
                     .placeholder(R.drawable.ic_launcher_background)
                     .error(R.drawable.ic_launcher_foreground).into(image, object :
                         com.squareup.picasso.Callback {
                         override fun onSuccess() {
                         }

                         override fun onError(e: Exception) {
                             e.printStackTrace()         // it will print the exception
                         }
                     })*/
        }
    }

    override fun getItemCount(): Int {
        return blogs.size
    }

    fun update(data: List<Blog>) {
        blogs = data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}