package com.example.quraanuploader.data

import com.example.quraanuploader.enities.Media
import com.github.kittinunf.fuel.core.Body
import com.github.kittinunf.fuel.core.awaitResult
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

object ApiClient : ApiEndPoints {

    private const val GET_MEDIA_URL = "https://qadi-quran.herokuapp.com/get/media"

    override suspend fun getMediaList(coroutineContext: CoroutineContext): Media? =
        withContext(coroutineContext) {
      return@withContext  GET_MEDIA_URL.httpGet().awaitResult(Media.MediaDeserializer()).fold({it},{null})
    }

}