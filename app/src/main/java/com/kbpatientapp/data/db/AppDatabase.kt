package com.kbpatientapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kbpatientapp.data.db.dao.PatientDao
import com.kbpatientapp.data.db.dao.VisitDao
import com.kbpatientapp.data.db.model.Patient
import com.kbpatientapp.data.db.model.Visit

@Database(entities = [Patient::class, Visit::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun visitDao(): VisitDao
}