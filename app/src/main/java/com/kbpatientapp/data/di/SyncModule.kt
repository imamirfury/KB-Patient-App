package com.kbpatientapp.data.di

import android.content.Context
import com.kbpatientapp.data.db.dao.PatientDao
import com.kbpatientapp.data.db.dao.VisitDao
import com.kbpatientapp.data.network.AppConnectivityMonitor
import com.kbpatientapp.data.network.NetworkConnectivityObserver
import com.kbpatientapp.data.network.NetworkConnectivityObserverImpl
import com.kbpatientapp.data.sync.SyncManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SyncModule {

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): NetworkConnectivityObserver =
        NetworkConnectivityObserverImpl(context)

    @Provides
    fun provideSyncManager(visitDao: VisitDao, patientDao: PatientDao): SyncManager =
        SyncManager(visitDao, patientDao)

    @Provides
    fun provideConnectivityMonitor(
        observer: NetworkConnectivityObserver,
        syncManager: SyncManager
    ) =
        AppConnectivityMonitor(observer, syncManager)
}