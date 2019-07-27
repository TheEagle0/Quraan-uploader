package com.example.quraanuploader.view_containers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quraanuploader.enities.Media
import com.example.quraanuploader.managers.ApiManager
import kotlinx.coroutines.launch

class MediaViewModel : ViewModel() {

    private val mediaList = MutableLiveData<List<Media.Data>>()

    fun getMediaList(): LiveData<List<Media.Data>> {
        if (mediaList.value == null) {
            viewModelScope.launch { mediaList.postValue(ApiManager.getAllMedia()?.data) }
        }
        return mediaList
    }
}