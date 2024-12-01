package com.example.sampleclarityinitialise

import android.app.Application
import android.util.Log

class SampleApp: Application() {

    override fun onCreate() {
        super.onCreate()

        Log.d("ClarityInit", "App onCreate Called")
    }
}