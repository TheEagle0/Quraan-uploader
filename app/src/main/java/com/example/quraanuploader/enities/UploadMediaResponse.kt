package com.example.quraanuploader.enities


import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class UploadMediaResponse(
    @SerializedName("data")
    val data: Data?
) {
    data class Data(
        @SerializedName("id")
        val id: String?,
        @SerializedName("is_list")
        val isList: Boolean?,
        @SerializedName("parent_id")
        val parentId: String?,
        @SerializedName("title")
        val title: String?
    )

    class MediaDeserializer : ResponseDeserializable<UploadMediaResponse> {
        override fun deserialize(content: String): UploadMediaResponse? =
            Gson().fromJson<UploadMediaResponse>(content, object : TypeToken<UploadMediaResponse>() {}.type)
    }
}