package com.kbpatientapp.data.di

import android.content.Context
import androidx.room.Room
import com.kbpatientapp.data.db.AppDatabase
import com.kbpatientapp.data.db.dao.PatientDao
import com.kbpatientapp.data.db.dao.VisitDao
import com.kbpatientapp.data.repository.PatientRepository
import com.kbpatientapp.data.repository.PatientRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "KhushiBaby_db"
        ).fallbackToDestructiveMigration(true).build()

    @Provides
    fun providePatientDao(db: AppDatabase): PatientDao = db.patientDao()

    @Provides
    fun provideVisitDao(db: AppDatabase): VisitDao = db.visitDao()


}