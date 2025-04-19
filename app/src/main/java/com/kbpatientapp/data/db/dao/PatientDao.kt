package com.kbpatientapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kbpatientapp.data.db.model.Patient
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert
    suspend fun insertPatient(patient: Patient): Long

    @Query("SELECT * FROM patients")
    fun getAllPatients(): Flow<List<Patient>>

    @Query("SELECT * FROM patients WHERE id = :id")
    suspend fun getPatient(id: Int): Patient


    @Query("SELECT * FROM patients WHERE isSynced = 0")
    fun getUnSyncedPatients(): Flow<List<Patient>>

    @Update
    fun  updatePatient(patient: Patient)

    @Update
    suspend fun markAsSynced(patient: Patient)
}