package com.kbpatientapp.data.network

import com.kbpatientapp.data.sync.SyncManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AppConnectivityMonitor @Inject constructor(
    private val observer: NetworkConnectivityObserver,
    private val syncManager: SyncManager
) {
    fun startMonitoring() {
        CoroutineScope(Dispatchers.Default).launch {
            observer.observe().collect { isConnected ->
                Timber.d( " Network Connection $isConnected")
                if (isConnected) {
                    syncManager.syncUnSyncedData()
                }
            }
        }
    }
}