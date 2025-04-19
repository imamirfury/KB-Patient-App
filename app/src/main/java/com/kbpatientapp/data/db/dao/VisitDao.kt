package com.kbpatientapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kbpatientapp.data.db.model.Visit
import kotlinx.coroutines.flow.Flow

@Dao
interface VisitDao {
    @Insert
    suspend fun insertVisit(visit: Visit): Long

    @Query("SELECT * FROM visits WHERE id = :visitId")
    fun getVisitById(visitId: Int): Flow<Visit>

    @Query("SELECT * FROM visits WHERE patientId = :patientId")
    fun getVisitsForPatient(patientId: Int): Flow<List<Visit>>

    @Update
    suspend fun updateVisit(visit: Visit)

    @Query("SELECT * FROM visits WHERE isSynced = 0")
    fun getUnSyncedVisits(): Flow<List<Visit>>

    @Update
    suspend fun markAsSynced(visit: Visit)
}