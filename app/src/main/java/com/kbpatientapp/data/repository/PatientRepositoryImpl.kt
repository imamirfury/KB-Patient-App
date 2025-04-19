package com.kbpatientapp.data.repository

import com.kbpatientapp.data.db.dao.PatientDao
import com.kbpatientapp.data.db.dao.VisitDao
import com.kbpatientapp.data.db.model.Patient
import com.kbpatientapp.data.db.model.Visit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val patientDao: PatientDao,
    private val visitDao: VisitDao
) : PatientRepository {

    override fun getAllPatients(): Flow<List<Patient>> = patientDao.getAllPatients()

    override suspend fun insertPatient(patient: Patient): Long = patientDao.insertPatient(patient)

    override suspend fun getPatient(id: Int): Patient = patientDao.getPatient(id)

    override suspend fun insertVisit(visit: Visit): Long = visitDao.insertVisit(visit)

    override  fun getVisitById(visitId: Int): Flow<Visit> = visitDao.getVisitById(visitId)

    override fun getVisitsForPatient(patientId: Int): Flow<List<Visit>> =
        visitDao.getVisitsForPatient(patientId)

    override suspend fun updateVisit(visit: Visit) = visitDao.updateVisit(visit)
}