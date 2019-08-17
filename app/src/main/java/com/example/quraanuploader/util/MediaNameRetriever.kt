package com.example.quraanuploader.util

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

object MediaNameRetriever {
    fun getMediaNameFromUri(uri: Uri, context: Context?): String? =
        context?.contentResolver?.query(uri, null, null, null, null)?.use {
            val nameIndex: Int = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            it.moveToFirst()
            val name = it.getString(nameIndex)
            it.close()
            return@use name
        }
}