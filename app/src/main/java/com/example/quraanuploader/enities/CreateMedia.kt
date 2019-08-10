package com.example.quraanuploader.enities

import com.google.gson.annotations.SerializedName

data class CreateMedia(@SerializedName("title") val title: String, @SerializedName("parent_id") val parentId: String)