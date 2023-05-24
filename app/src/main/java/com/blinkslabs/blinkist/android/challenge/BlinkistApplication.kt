package com.blinkslabs.blinkist.android.challenge

import android.app.Application
import com.facebook.shimmer.BuildConfig
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BlinkistApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeTimber()
        initializeThreeTen()
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initializeThreeTen() = AndroidThreeTen.init(this)
}
