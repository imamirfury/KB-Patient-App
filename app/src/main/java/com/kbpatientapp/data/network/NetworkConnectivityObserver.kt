package com.kbpatientapp.data.network

import kotlinx.coroutines.flow.Flow

interface NetworkConnectivityObserver{
    fun observe() : Flow<Boolean>

}