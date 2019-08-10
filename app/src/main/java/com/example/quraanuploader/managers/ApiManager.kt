package com.example.quraanuploader.managers

import com.example.quraanuploader.data.ApiClient
import com.example.quraanuploader.enities.CreateMedia
import com.example.quraanuploader.enities.CreateMediaRsponse
import com.example.quraanuploader.enities.Media
import kotlinx.coroutines.Dispatchers

object ApiManager {


    private val media = suspend { ApiClient.getMediaList(Dispatchers.Default) }

    suspend fun getAllMedia(): Media? {
        return media()
    }

    suspend fun createListAsync(createMedia: CreateMedia): CreateMediaRsponse? {
        return ApiClient.createList(createMedia, coroutineContext = Dispatchers.Default)
    }
}