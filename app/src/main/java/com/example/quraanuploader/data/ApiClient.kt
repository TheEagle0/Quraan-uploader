package com.example.quraanuploader.data

import com.example.quraanuploader.enities.CreateMedia
import com.example.quraanuploader.enities.CreateMediaRsponse
import com.example.quraanuploader.enities.Media
import com.github.kittinunf.fuel.core.Body
import com.github.kittinunf.fuel.core.awaitResult
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

object ApiClient : ApiEndPoints {


    private const val GET_MEDIA_URL = "https://qadi-quran.herokuapp.com/get/media"
    private const val CREATE_LIST_URL = "https://qadi-quran.herokuapp.com/create/media"

    override suspend fun getMediaList(coroutineContext: CoroutineContext): Media? =
        withContext(coroutineContext) {
            return@withContext GET_MEDIA_URL.httpGet().awaitResult(Media.MediaDeserializer())
                .fold({ it }, { null })
        }

    override suspend fun createList(
        createMedia: CreateMedia,
        coroutineContext: CoroutineContext
    ): CreateMediaRsponse? =
        withContext(coroutineContext) {
            val body = Gson().toJson(createMedia)
            return@withContext CREATE_LIST_URL.httpPost()
                .header("Content-Type" to "application/json").body(body)
                .awaitResult(CreateMediaRsponse.CreateMediaDeserializer()).fold({ it }, { null })
        }

}