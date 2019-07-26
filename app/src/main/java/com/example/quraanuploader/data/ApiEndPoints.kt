package com.example.quraanuploader.data

import com.example.quraanuploader.enities.Media
import io.reactivex.Single

interface ApiEndPoints {

    fun getMediaList(): Single<Media>

}