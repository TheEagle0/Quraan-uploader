package com.example.quraanuploader.enities


import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class DeleteMediaResponse(
    @SerializedName("data")
    val data: List<Boolean>
) {
    class DeleteMediaDesrializer : ResponseDeserializable<DeleteMediaResponse> {
        override fun deserialize(content: String): DeleteMediaResponse? =
            Gson().fromJson<DeleteMediaResponse>(
                content,
                object : TypeToken<DeleteMediaResponse>() {}.type
            )
    }
}