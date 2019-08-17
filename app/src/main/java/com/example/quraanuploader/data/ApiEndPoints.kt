package com.example.quraanuploader.data

import android.app.Application
import android.content.Context
import com.example.quraanuploader.enities.*
import com.github.kittinunf.fuel.core.FileDataPart
import com.vincent.filepicker.filter.entity.AudioFile
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.io.InputStream
import kotlin.coroutines.CoroutineContext

interface ApiEndPoints {

    suspend fun getMediaList(coroutineContext: CoroutineContext): Media?
    suspend fun createList(createMedia: CreateMedia,coroutineContext: CoroutineContext):CreateMediaRsponse?
    suspend fun deleteMedia(deleteMedia: DeleteMedia,coroutineContext: CoroutineContext):DeleteMediaResponse?
    suspend fun upploadMedia(uploadFile: UploadFile, file:InputStream, context: Context, coroutineContext: CoroutineContext):UploadMediaResponse?
}