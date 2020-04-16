package com.example.mvvm_kotlin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Blog(
    @SerializedName("title")
    var title: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("imageHref")
    var imageHref: String? = null
) : Serializable