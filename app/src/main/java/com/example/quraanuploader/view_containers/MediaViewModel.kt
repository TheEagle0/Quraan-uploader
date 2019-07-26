package com.example.quraanuploader.view_containers

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quraanuploader.app.UploaderApp.Companion.appInstance
import com.example.quraanuploader.data.ApiClient
import com.example.quraanuploader.enities.Media
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import kotlin.coroutines.CoroutineContext

class MediaViewModel : AndroidViewModel(appInstance) {
    private val mediaList = MutableLiveData<List<Media.Data>>()

    fun getMediaList(): LiveData<List<Media.Data>> {
        if (mediaList.value == null) {
        val dataFlowable=ApiClient.getMediaList().toFlowable()

        }
        return mediaList
    }
}