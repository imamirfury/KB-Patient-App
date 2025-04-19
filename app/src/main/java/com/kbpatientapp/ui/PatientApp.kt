package com.kbpatientapp.ui

import android.app.Application
import com.kbpatientapp.data.network.AppConnectivityMonitor
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class PatientApp : Application() {

    @Inject
    lateinit var connectivityMonitor: AppConnectivityMonitor

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        connectivityMonitor.startMonitoring()
    }
}