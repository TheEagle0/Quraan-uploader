package com.example.quraanuploader.data

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.quraanuploader.enities.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.InlineDataPart
import com.github.kittinunf.fuel.core.awaitResult
import com.github.kittinunf.fuel.core.requests.upload
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.util.encodeBase64ToString
import com.google.gson.Gson
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.coroutines.CoroutineContext

object ApiClient : ApiEndPoints {

    private const val GET_MEDIA_URL = "https://qadi-quran.herokuapp.com/get/media"
    private const val CREATE_LIST_URL = "https://qadi-quran.herokuapp.com/create/media"
    private const val DELETE_MEDIA = "https://qadi-quran.herokuapp.com/delete/media"
    private const val UPLOAD_MEDIA = "https://qadi-quran.herokuapp.com"

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

    override suspend fun deleteMedia(
        deleteMedia: DeleteMedia,
        coroutineContext: CoroutineContext
    ): DeleteMediaResponse? =
        withContext(coroutineContext) {
            val body = Gson().toJson(deleteMedia)
            return@withContext DELETE_MEDIA.httpPost().header("Content-Type" to "application/json")
                .body(body).awaitResult(DeleteMediaResponse.DeleteMediaDesrializer())
                .fold({ it }, { null })
        }

    override suspend fun upploadMedia(
        uploadFile: UploadFile,
        file: InputStream,
        context: Context,
        coroutineContext: CoroutineContext
    ): UploadMediaResponse? =
        withContext(coroutineContext) {
          File("${context.dataDir.absolutePath}/${uploadFile.title}").outputStream()
            .use { output: FileOutputStream -> file.use { input: InputStream -> input.copyTo(output)
            } }
            return@withContext  Fuel.post(
                Uri.parse(UPLOAD_MEDIA).buildUpon().appendPath("upload").appendPath("media")
                    .toString()
            ).upload()
                .add(FileDataPart.from("${context.dataDir.absolutePath}/${uploadFile.title}", name = uploadFile.title))
                .add(InlineDataPart(uploadFile.id, "parent_id"))
                .add(InlineDataPart(uploadFile.title.encodeBase64ToString(), "title"))
                .awaitResult(UploadMediaResponse.MediaDeserializer()).fold({ Log.d("response",it.toString());it},{null})
        }
}
