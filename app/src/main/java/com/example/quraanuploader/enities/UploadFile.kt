package com.example.quraanuploader.enities

import com.google.gson.annotations.SerializedName

data class UploadFile( @SerializedName("parent_id") val id: String, @SerializedName("title") val title: String)