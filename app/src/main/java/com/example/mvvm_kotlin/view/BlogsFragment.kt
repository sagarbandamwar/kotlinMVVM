package com.example.mvvm_kotlin.view

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_kotlin.R
import com.example.mvvm_kotlin.database.BlogsDao
import com.example.mvvm_kotlin.database.BlogsDatabase
import com.example.mvvm_kotlin.di.Injection
import com.example.mvvm_kotlin.model.Blog
import com.example.mvvm_kotlin.util.CommonUtil
import com.example.mvvm_kotlin.viewmodel.BlogsViewModel
import kotlinx.android.synthetic.main.fragment_blog.*

@TargetApi(Build.VERSION_CODES.M)
class BlogsFragment : Fragment() {

    private lateinit var viewModel: BlogsViewModel
    private lateinit var adapter: BlogsAdapter
    private lateinit var blogsDao: BlogsDao
    private var arrayListBlogs: List<Blog> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        blogsDao = BlogsDatabase.getDatabase(requireContext())?.blogsDao()!!
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
        if (!arrayListBlogs.isEmpty()) {
            blogsDao.setBlogs(arrayListBlogs)
        }

        swipe_referesh_layout.setOnRefreshListener {
            setupUI()
            swipe_referesh_layout.isRefreshing = false
        }
    }
    //ui
    private fun setupUI() {
        if (CommonUtil.isOnline(requireContext())) {
            adapter = BlogsAdapter(viewModel.blogsLiveData.value ?: emptyList())
            recyclerView.adapter = adapter
        } else {
            adapter = BlogsAdapter(arrayListBlogs)
            recyclerView.adapter = adapter
        }
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
        arrayListBlogs = it
        adapter.update(it)
    }

    private val onMessageErrorObserver = Observer<Any> {
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
    }

    private val emptyListObserver = Observer<Boolean> {
        layoutEmpty.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
    }

    private fun addObserver() {
        viewModel.blogsLiveData.observe(viewLifecycleOwner, renderBlogs)
        viewModel.onMessageError.observe(viewLifecycleOwner, onMessageErrorObserver)
        viewModel.isEmptyList.observe(viewLifecycleOwner, emptyListObserver)
    }

    //If you require updated data, you can call the method "loadMuseum" here
    override fun onResume() {
        super.onResume()
        viewModel.loadBlogs()
    }
}