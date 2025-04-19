package com.kbpatientapp.data.repository

import com.kbpatientapp.data.db.model.Patient
import com.kbpatientapp.data.db.model.Visit
import kotlinx.coroutines.flow.Flow

interface PatientRepository {

    fun getAllPatients(): Flow<List<Patient>>

    suspend fun insertPatient(patient: Patient): Long

    suspend fun getPatient(id: Int): Patient

    suspend fun insertVisit(visit: Visit): Long

    fun getVisitById(visitId : Int) : Flow<Visit>

    fun getVisitsForPatient(patientId: Int): Flow<List<Visit>>

    suspend fun updateVisit(visit: Visit)

}