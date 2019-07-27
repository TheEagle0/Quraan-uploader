package com.example.quraanuploader.managers

import com.example.quraanuploader.data.ApiClient
import com.example.quraanuploader.enities.Media
import kotlinx.coroutines.Dispatchers

object ApiManager {

    private val media = suspend {ApiClient.getMediaList(Dispatchers.Default)}

    suspend fun getAllMedia(): Media? {
        return media()
    }
}