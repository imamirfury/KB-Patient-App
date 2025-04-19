package com.kbpatientapp.data.di

import com.kbpatientapp.data.db.dao.PatientDao
import com.kbpatientapp.data.db.dao.VisitDao
import com.kbpatientapp.data.repository.PatientRepository
import com.kbpatientapp.data.repository.PatientRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providePatientRepository(patientDao: PatientDao, visitDao: VisitDao): PatientRepository =
        PatientRepositoryImpl(patientDao, visitDao)
}