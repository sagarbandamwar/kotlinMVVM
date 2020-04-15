package com.example.mvvm_kotlin

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mvvm_kotlin.model.Blog
import com.example.mvvm_kotlin.model.BlogDataSource
import com.example.mvvm_kotlin.services.NetworkOperationCallback
import com.example.mvvm_kotlin.viewmodel.BlogsViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*

@Mock
private lateinit var repository: BlogDataSource
@Mock
private lateinit var context: Application

@Captor
private lateinit var networkOperationCallbackCaptor: ArgumentCaptor<NetworkOperationCallback<Blog>>

private lateinit var viewModel:BlogsViewModel

private lateinit var isViewLoadingObserver: Observer<Boolean>
private lateinit var onMessageErrorObserver: Observer<Any>
private lateinit var emptyListObserver: Observer<Boolean>
private lateinit var onRenderMuseumsObserver: Observer<List<Blog>>

private lateinit var blogEmptyList:List<Blog>
private lateinit var blogList:List<Blog>

/**
//https://jeroenmols.com/blog/2019/01/17/livedatajunit5/
//Method getMainLooper in android.os.Looper not mocked

java.lang.IllegalStateException: operationCallbackCaptor.capture() must not be null
 */
@get:Rule
val rule = InstantTaskExecutorRule()

@Test
fun `retrieve museums with ViewModel and Repository returns empty data`(){
    with(viewModel){
        loadMuseums()
        isViewLoading.observeForever(isViewLoadingObserver)
        //onMessageError.observeForever(onMessageErrorObserver)
        isEmptyList.observeForever(emptyListObserver)
        blogsLiveData.observeForever(onRenderMuseumsObserver)
    }

    Mockito.verify(repository, Mockito.times(1)).retrieveBlogs(capture(networkOperationCallbackCaptor))
    networkOperationCallbackCaptor.value.onSuccess(blogEmptyList)

    Assert.assertNotNull(viewModel.isViewLoading.value)
    //Assert.assertNotNull(viewModel.onMessageError.value) //java.lang.AssertionError
    //Assert.assertNotNull(viewModel.isEmptyList.value)
    Assert.assertTrue(viewModel.isEmptyList.value==true)
    Assert.assertTrue(viewModel.blogsLiveData.value?.size==0)
}

@Test
fun `retrieve museums with ViewModel and Repository returns full data`(){
    with(viewModel){
        loadMuseums()
        isViewLoading.observeForever(isViewLoadingObserver)
        blogsLiveData.observeForever(onRenderMuseumsObserver)
    }

    Mockito.verify(repository, Mockito.times(1)).retrieveBlogs(capture(networkOperationCallbackCaptor))
    networkOperationCallbackCaptor.value.onSuccess(blogList)

    Assert.assertNotNull(viewModel.isViewLoading.value)
    Assert.assertTrue(viewModel.blogsLiveData.value?.size==3)
}

@Test
fun `retrieve museums with ViewModel and Repository returns an error`(){
    with(viewModel){
        loadMuseums()
        isViewLoading.observeForever(isViewLoadingObserver)
        onMessageError.observeForever(onMessageErrorObserver)
    }
    Mockito.verify(repository, Mockito.times(1)).retrieveBlogs(capture(networkOperationCallbackCaptor))
    networkOperationCallbackCaptor.value.onError("An error occurred")
    Assert.assertNotNull(viewModel.isViewLoading.value)
    Assert.assertNotNull(viewModel.onMessageError.value)
}

private fun setupObservers(){
    isViewLoadingObserver= Mockito.mock(Observer::class.java) as Observer<Boolean>
    onMessageErrorObserver= Mockito.mock(Observer::class.java) as Observer<Any>
    emptyListObserver= Mockito.mock(Observer::class.java) as Observer<Boolean>
    onRenderMuseumsObserver= Mockito.mock(Observer::class.java) as Observer<List<Blog>>
}

private fun mockData(){
    blogEmptyList= emptyList()
    val mockList:MutableList<Blog>  = mutableListOf()
    mockList.add(Blog("Beavers","Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony","http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"))
    blogList= mockList.toList()
}