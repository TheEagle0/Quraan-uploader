package com.example.quraanuploader.data

import com.example.quraanuploader.enities.Media
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.rx.rxObject
import io.reactivex.Single
import java.lang.Exception

object ApiClient : ApiEndPoints {

    private const val getMediaUrl = "https://qadi-quran.herokuapp.com/get/media"

    override fun getMediaList(): Single<Media> {
        return getMediaUrl.httpGet().rxObject(Media.mediaDeserializer())
            .map { it.component1() ?: throw it.component2() ?: throw Exception() }
    }
}