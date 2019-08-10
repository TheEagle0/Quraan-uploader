package com.example.quraanuploader.view_containers

import android.app.Application
import androidx.lifecycle.*
import com.example.quraanuploader.enities.CreateMedia
import com.example.quraanuploader.enities.CreateMediaRsponse
import com.example.quraanuploader.enities.Media
import com.example.quraanuploader.managers.ApiManager
import kotlinx.coroutines.launch

class MediaViewModel(appInstance: Application) : AndroidViewModel(appInstance) {

    private val liveMediaList = MutableLiveData<List<Media.Data>>()
    val liveLoading = MutableLiveData<Boolean>()
    private val liveCreateMedia = MutableLiveData<CreateMediaRsponse>()

    fun getMediaList(): LiveData<List<Media.Data>> {
        if (liveMediaList.value == null) {
            viewModelScope.launch {
                liveLoading.postValue(true)
                liveMediaList.postValue(ApiManager.getAllMedia()?.data)
                liveLoading.postValue(false)
            }
        }
        return liveMediaList
    }

    fun createList(createMedia: CreateMedia): LiveData<CreateMediaRsponse> {
        if (liveCreateMedia.value == null)
            viewModelScope.launch {
                liveLoading.postValue(true)
                liveCreateMedia.postValue(ApiManager.createListAsync(createMedia))
                liveLoading.postValue(false)
            }
        return liveCreateMedia
    }
}