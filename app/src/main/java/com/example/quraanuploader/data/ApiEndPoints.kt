package com.example.quraanuploader.data

import com.example.quraanuploader.enities.*
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface ApiEndPoints {

    suspend fun getMediaList(coroutineContext: CoroutineContext): Media?
    suspend fun createList(createMedia: CreateMedia,coroutineContext: CoroutineContext):CreateMediaRsponse?
    suspend fun deleteMedia(deleteMedia: DeleteMedia,coroutineContext: CoroutineContext):DeleteMediaResponse?

}