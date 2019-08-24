package com.example.quraanuploader.view_containers

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.example.quraanuploader.R
import com.example.quraanuploader.app.UploaderApp
import com.example.quraanuploader.enities.*
import com.example.quraanuploader.managers.ApiManager
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch
import java.io.InputStream

class MediaViewModel(appInstance: Application) : AndroidViewModel(appInstance) {
    private val app = UploaderApp.appInstance
    val liveMediaList = MutableLiveData<List<Media.Data>>()
    val liveLoading = MutableLiveData<Boolean>()
    private val liveCreateMedia = MutableLiveData<CreateMediaRsponse>()

    fun getMediaList(): LiveData<List<Media.Data>> {
        viewModelScope.launch {
            liveLoading.postValue(true)
            liveMediaList.postValue(ApiManager.getAllMedia()?.data)
            liveLoading.postValue(false)
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

    fun deleteMedia(deleteMedia: DeleteMedia) {
        viewModelScope.launch {
            liveLoading.postValue(true)
            ApiManager.deleteMediaAsync(deleteMedia)?.apply {
                if (this.data.none { !it })
                    Toasty.success(
                        app,
                        app.getString(R.string.file_deleted),
                        Toast.LENGTH_SHORT
                    ).show()
                else Toasty.error(app, app.getString(R.string.file_not_deleted)).show()
            }
            liveLoading.postValue(false)

        }
    }

    fun uploadMedia(uploadFile: UploadFile, file: InputStream, context: Context) {
        viewModelScope.launch {
            liveLoading.postValue(true)
            ApiManager.uploadMediaAsync(uploadFile, file, context)?.apply {
                if (this.data?.id != null)
                    Toasty.success(app, "File Uploaded successfully", Toast.LENGTH_SHORT).show()
            }
            liveLoading.postValue(false)
        }
    }
}