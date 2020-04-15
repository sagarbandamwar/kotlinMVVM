package com.example.mvvm_kotlin.view

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
import com.example.mvvm_kotlin.viewmodel.BlogsViewModel
import kotlinx.android.synthetic.main.fragment_blog.*
import kotlinx.android.synthetic.main.layout_error.*

class BlogsFragment  : Fragment() {

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
    private fun setupUI(){
        adapter= BlogsAdapter(viewModel.blogsLiveData.value?: emptyList())
        recyclerView.adapter= adapter
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory()).get(BlogsViewModel::class.java)

    }

    //observers
    private val renderBlogs= Observer<List<Blog>> {
        layoutError.visibility= View.GONE
        layoutEmpty.visibility= View.GONE
        adapter.update(it)

    }

    private val isViewLoadingObserver= Observer<Boolean> {
        val visibility=if(it) View.VISIBLE else View.GONE
        progressBar.visibility= visibility
    }

    private val onMessageErrorObserver= Observer<Any> {
        layoutError.visibility= View.VISIBLE
        layoutEmpty.visibility= View.GONE
        textViewError.text= "Error $it"
    }

    private val emptyListObserver= Observer<Boolean> {
        layoutEmpty.visibility= View.VISIBLE
        layoutError.visibility= View.GONE
    }

    private fun addObserver(){
        viewModel.blogsLiveData.observe(viewLifecycleOwner,renderBlogs)
        viewModel.isViewLoading.observe(viewLifecycleOwner,isViewLoadingObserver)
        viewModel.onMessageError.observe(viewLifecycleOwner,onMessageErrorObserver)
        viewModel.isEmptyList.observe(viewLifecycleOwner,emptyListObserver)
    }
    //If you require updated data, you can call the method "loadMuseum" here
    override fun onResume() {
        super.onResume()
        viewModel.loadMuseums()
    }
}