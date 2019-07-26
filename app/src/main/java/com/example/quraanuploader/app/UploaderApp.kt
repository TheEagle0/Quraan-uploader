package com.example.quraanuploader.app

import android.app.Application

class UploaderApp:Application() {
    companion object {
        private lateinit var mutableAppInstance: UploaderApp
        val appInstance by lazy { mutableAppInstance }
    }
    override fun onCreate() {
        super.onCreate()
        mutableAppInstance=this
    }
}