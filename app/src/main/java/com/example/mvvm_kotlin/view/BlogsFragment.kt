package com.example.mvvm_kotlin.view

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_kotlin.R
import com.example.mvvm_kotlin.di.Injection
import com.example.mvvm_kotlin.model.Blog
import com.example.mvvm_kotlin.util.CommonUtil
import com.example.mvvm_kotlin.viewmodel.BlogsViewModel
import kotlinx.android.synthetic.main.fragment_blog.*

@TargetApi(Build.VERSION_CODES.M)
class BlogsFragment : Fragment() {

    private lateinit var viewModel: BlogsViewModel
    private lateinit var adapter: BlogsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addObserver()
        setupUI()
    }

    //ui
    private fun setupUI() {
        adapter = BlogsAdapter(viewModel.blogsLiveData.value ?: emptyList())
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(BlogsViewModel::class.java)
    }

    //observers
    private val renderBlogs = Observer<List<Blog>> {
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        adapter.update(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        progressBar.visibility = visibility
    }

    private val onMessageErrorObserver = Observer<Any> {
        if (CommonUtil.isOnline(activity as Context)) {
            layoutError.visibility = View.GONE

        } else {
            CommonUtil.showDialog(
                resources.getString(R.string.internet_message),
                activity as Context
            )
            layoutEmpty.visibility = View.GONE
        }
    }

    private val emptyListObserver = Observer<Boolean> {
        layoutEmpty.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
    }

    private fun addObserver() {
        viewModel.blogsLiveData.observe(viewLifecycleOwner, renderBlogs)
        viewModel.isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
        viewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        viewModel.isEmptyList.observe(viewLifecycleOwner, emptyListObserver)
    }

    //If you require updated data, you can call the method "loadMuseum" here
    override fun onResume() {
        super.onResume()
        viewModel.loadBlogs()
    }
}