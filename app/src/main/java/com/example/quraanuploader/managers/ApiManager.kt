package com.example.quraanuploader.managers

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.quraanuploader.data.ApiClient
import com.example.quraanuploader.enities.*
import com.vincent.filepicker.filter.entity.AudioFile
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.io.InputStream

object ApiManager {


    private val media = suspend { ApiClient.getMediaList(Dispatchers.Default) }

    suspend fun getAllMedia(): Media? {
        return media()
    }

    suspend fun createListAsync(createMedia: CreateMedia): CreateMediaRsponse? {
        return ApiClient.createList(createMedia, coroutineContext = Dispatchers.Default)
    }

    suspend fun deleteMediaAsync(deleteMedia: DeleteMedia): DeleteMediaResponse? {
        return ApiClient.deleteMedia(deleteMedia, Dispatchers.Default)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun uploadMediaAsync(uploadFile: UploadFile, file: InputStream, context: Context) :UploadMediaResponse?{
        return ApiClient.upploadMedia(uploadFile,file,context, Dispatchers.Default)
    }
}