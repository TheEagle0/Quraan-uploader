package com.example.quraanuploader.data

import com.example.quraanuploader.enities.Media
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface ApiEndPoints {

    suspend fun getMediaList(coroutineContext: CoroutineContext): Media?

}